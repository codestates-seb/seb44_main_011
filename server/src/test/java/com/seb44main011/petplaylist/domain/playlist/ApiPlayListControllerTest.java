package com.seb44main011.petplaylist.domain.playlist;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.google.gson.Gson;
import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.member.service.MemberService;
import com.seb44main011.petplaylist.domain.member.stub.MemberTestData;
import com.seb44main011.petplaylist.domain.music.dto.MusicDto;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.service.MusicService;
import com.seb44main011.petplaylist.domain.music.stub.TestData;
import com.seb44main011.petplaylist.domain.playlist.dto.PlaylistDto;
import com.seb44main011.petplaylist.domain.playlist.entity.entityTable.PlayList;
import com.seb44main011.petplaylist.domain.playlist.mapper.MusicListMapper;

import com.seb44main011.petplaylist.domain.playlist.service.MusicListService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiPlayListControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;

    @MockBean
    private MemberService memberService;
    @MockBean
    private MusicService musicService;
    @MockBean
    private MusicListService musicListService;

    @MockBean
    private MusicListMapper musicListMapper;

    private final String API_PLAYLIST_URL = "/api/playlist";
    private final String API_PLAYLIST_GET_URL = "/api/playlist/{dogOrCats}/id/{member-id}";

    @Test
    @DisplayName("개인 플레이 리스트 추가(좋아요) 기능 테스트")
    public void postPersonalPlayListTest() throws Exception {
        MusicDto.PostRequest postRequestData = TestData.MockMusic.getPostRequestData();
        PlayList mockmusicList =TestData.MockMusicList.getMusicListData();
        String content = gson.toJson(postRequestData);

        given(memberService.findMember(Mockito.anyLong())).willReturn(MemberTestData.MockMember.getMemberData());
        given(musicService.findMusic(Mockito.anyLong())).willReturn(TestData.MockMusic.getMusicData());
        given(musicListMapper.memberAndMusicToMusicList(Mockito.any(Member.class),Mockito.any(Music.class))).willReturn(mockmusicList);
        doNothing().when(musicListService).addMusicList(Mockito.any(PlayList.class));

        ResultActions actions =
                mockMvc.perform(
                        post(API_PLAYLIST_URL+"/{member-id}", postRequestData.getMusicId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)

                )
                        .andExpect(status().isCreated())
                        .andDo(
                                MockMvcRestDocumentationWrapper.document("개인 플레이 리스트(좋아요)에 곡 추가"
                                        ,preprocessRequest(prettyPrint())
                                        ,preprocessResponse(prettyPrint()),
                                        resource(
                                                ResourceSnippetParameters.builder()
                                                        .description("개인 플레이 리스트(좋아요)에 곡 추가 기능")
                                                        .pathParameters(
                                                                parameterWithName("member-id").description("회원 식별 Id")
                                                        )
                                                        .requestFields(
                                                                fieldWithPath("musicId").type(JsonFieldType.NUMBER).description("음악 식별 Id")
                                                        )
                                                        .build()
                                                        )
                                )
                        );
    }
    @Test
    @DisplayName("회원의 개인 플레이 리스트 조회(좋아요 리스트)")
    public void getAllMusicListTest() throws Exception{
        List<PlaylistDto.ApiResponse> responseList =TestData.ResponseData.Api.getPlayListResponseList();
        Page<PlayList> pageTestData = TestData.ResponseData.PageNationData.getPageData(1,responseList.size());

        given(musicListService.findPersonalMusicListsPage(Mockito.anyLong(),Mockito.anyInt())).willReturn(pageTestData);
        given(musicListMapper.musicListToPlayListResponseList(Mockito.anyList())).willReturn(responseList);

        ResultActions actions =
                mockMvc.perform(
                                get(API_PLAYLIST_URL+"/{member-id}",1)
                                        .accept(MediaType.APPLICATION_JSON)
                                        .param("page", String.valueOf(pageTestData.getNumber()+1))

                        )
                        .andExpect(status().isOk())
                        .andDo(
                                MockMvcRestDocumentationWrapper.document("회원의 개인 플레이 리스트 조회(좋아요 리스트)"
                                        ,preprocessRequest(prettyPrint())
                                        ,preprocessResponse(prettyPrint()),
                                        resource(
                                                ResourceSnippetParameters.builder()
                                                        .description("개인 플레이 리스트 조회(좋아요 리스트) API")
                                                        .pathParameters(
                                                                parameterWithName("member-id").type(SimpleType.NUMBER).description("회원 식별 Id")
                                                        )
                                                        .requestParameters(
                                                                parameterWithName("page").type(SimpleType.NUMBER).description("가져올 페이지 숫자")
                                                        )
                                                        .responseFields(
                                                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                                                fieldWithPath("data.[].musicId").type(JsonFieldType.NUMBER).description("음악 식별 Id"),
                                                                fieldWithPath("data.[].title").type(JsonFieldType.STRING).description("음악 타이틀(제목)"),
                                                                fieldWithPath("data.[].music_url").type(JsonFieldType.STRING).description("음악의 URL"),
                                                                fieldWithPath("data.[].image_url").type(JsonFieldType.STRING).description("음악 이미지의 URL"),
                                                                fieldWithPath("data.[].category").type(JsonFieldType.STRING).description("조회한 카테고리"),
                                                                fieldWithPath("data.[].tags").type(JsonFieldType.STRING).description("조회한 태그"),
                                                                fieldWithPath("data.[].liked").type(JsonFieldType.BOOLEAN).description("좋아요 여부(개인 플레이 리스트)"),
                                                                fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이징 정보"),
                                                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("한 페이지에 속하는 데이터 개수"),
                                                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 데이터 개수"),
                                                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 개수")
                                                        )
                                                        .build()
                                        )
                                )
                        );



    }

    @Test
    @DisplayName("카테고리 태그별 조회 테스트(로그인 된 회원)")
    public void getCategoryAndTagFromMemberTest() throws Exception {

        List<PlaylistDto.ApiResponse> apiResponses = TestData.ResponseData.Api.getApiCategorySerchPlayListResponseList();
        Page<Music> testPageData = TestData.ResponseData.PageNationData.getPageData(1,apiResponses.size()+50);

        given(musicService.findCategoryAndTagsPageMusic(Mockito.any(Music.Category.class),Mockito.anyString(),Mockito.anyInt())).willReturn(testPageData);
        given(memberService.findMember(Mockito.anyLong())).willReturn(MemberTestData.MockMember.getMemberData());
        given(musicListMapper.musicListToCategoryPlayListApiResponse(Mockito.anyList(),Mockito.anyList())).willReturn(apiResponses);

        ResultActions actions =
                mockMvc.perform(
                                get(API_PLAYLIST_GET_URL,"DOGS",1)
                                        .accept(MediaType.APPLICATION_JSON)
                                        .param("page", String.valueOf(testPageData.getNumber()+1))
                                        .param("tags",apiResponses.get(0).getCategory())

                        )
                        .andExpect(status().isOk())
                        .andDo(
                                MockMvcRestDocumentationWrapper.document("카테고리 테크별 조회 API(로그인 된 회원)"
                                        ,preprocessRequest(prettyPrint())
                                        ,preprocessResponse(prettyPrint()),
                                        resource(
                                                ResourceSnippetParameters.builder()
                                                        .description("로그인 상태인 회원의 카테고리 테크별 조회 API")
                                                        .pathParameters(
                                                                parameterWithName("dogOrCats").type(SimpleType.STRING).description("카테고리(DOGS,CATS)"),
                                                                parameterWithName("member-id").type(SimpleType.NUMBER).description("회원 식별 Id")
                                                        )
                                                        .requestParameters(
                                                                parameterWithName("page").type(SimpleType.NUMBER).description("가져올 페이지 숫자"),
                                                                parameterWithName("tags").type(SimpleType.STRING).description("조회 할 태그").optional()
                                                        )
                                                        .responseFields(
                                                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                                                fieldWithPath("data.[].musicId").type(JsonFieldType.NUMBER).description("음악 식별 Id"),
                                                                fieldWithPath("data.[].title").type(JsonFieldType.STRING).description("음악 타이틀(제목)"),
                                                                fieldWithPath("data.[].music_url").type(JsonFieldType.STRING).description("음악의 URL"),
                                                                fieldWithPath("data.[].image_url").type(JsonFieldType.STRING).description("음악 이미지의 URL"),
                                                                fieldWithPath("data.[].category").type(JsonFieldType.STRING).description("조회한 카테고리"),
                                                                fieldWithPath("data.[].tags").type(JsonFieldType.STRING).description("조회한 태그"),
                                                                fieldWithPath("data.[].liked").type(JsonFieldType.BOOLEAN).description("좋아요 여부(개인 플레이 리스트)"),
                                                                fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이징 정보"),
                                                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("한 페이지에 속하는 데이터 개수"),
                                                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 데이터 개수"),
                                                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 개수")
                                                        )
                                                        .build()
                                        )
                                )
                        );



    }

    @Test
    @DisplayName("개인 플레이 리스트 삭제(좋아요 삭제) 기능 테스트")
    public void deletePersonalPlayListTest() throws Exception {

        MusicDto.DeleteRequest postRequestData = TestData.MockMusic.getDeleteRequestData();
        String content = gson.toJson(postRequestData);

        doNothing().when(musicListService).deleteMusicList(Mockito.any(Member.class),Mockito.any(Music.class));


        ResultActions actions =
                mockMvc.perform(
                                delete(API_PLAYLIST_URL+"/{member-id}", postRequestData.getMusicId())
                                        .accept(MediaType.APPLICATION_JSON)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(content)

                        )
                        .andExpect(status().isNoContent())
                        .andDo(
                                MockMvcRestDocumentationWrapper.document("개인 플레이 리스트(좋아요) 곡 삭제"
                                        ,preprocessRequest(prettyPrint())
                                        ,preprocessResponse(prettyPrint()),
                                        resource(
                                                ResourceSnippetParameters.builder()
                                                        .description("개인 플레이 리스트(좋아요) 곡 삭제 기능")
                                                        .pathParameters(
                                                                parameterWithName("member-id").description("회원 식별 Id")
                                                        )
                                                        .requestFields(
                                                                fieldWithPath("musicId").type(JsonFieldType.NUMBER).description("음악 식별 Id")
                                                        )
                                                        .build()
                                        )
                                )
                        );


    }
}
