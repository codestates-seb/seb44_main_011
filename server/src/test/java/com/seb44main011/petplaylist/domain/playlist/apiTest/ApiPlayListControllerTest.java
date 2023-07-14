package com.seb44main011.petplaylist.domain.playlist.apiTest;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.google.gson.Gson;
import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.member.service.MemberService;
import com.seb44main011.petplaylist.domain.member.stub.MemberTestData;
import com.seb44main011.petplaylist.domain.music.dto.MusicDto;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.service.mainService.MusicService;
import com.seb44main011.petplaylist.domain.music.service.storageService.S3Service;
import com.seb44main011.petplaylist.domain.music.stub.TestData;
import com.seb44main011.petplaylist.domain.playlist.dto.PlaylistDto;
import com.seb44main011.petplaylist.domain.playlist.entity.entityTable.PlayList;
import com.seb44main011.petplaylist.domain.playlist.mapper.MusicListMapper;
import com.seb44main011.petplaylist.domain.playlist.service.MusicListService;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.security.test.context.support.WithMockUser;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiPlayListControllerTest extends ApiFieldDescriptor{
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
    @MockBean
    private S3Service service;

    private final String API_PLAYLIST_URL = "/api/playlist";
    private final String API_PLAYLIST_GET_URL = "/api/playlist/{dogOrCats}/id/{member-id}";
    private List<PlaylistDto.ApiResponse> pageContentResponse;
    @BeforeEach
    void init(){
        this.pageContentResponse=TestData.ResponseData.Api.getPlayListResponseList();
    }

    @Test
    @DisplayName("전체 음악 리스트 조회 기능 테스트")
    @WithMockUser
    public void getAllMusicListFromMemberTest() throws Exception {
        Page<Music> pageTestData = TestData.ResponseData.PageNationData.getPageData(1,pageContentResponse.size());

        given(musicListService.findPersonalMusicLists(Mockito.anyLong())).willReturn(List.of(TestData.MockMusicList.getMusicListData()));
        given(musicService.findMusicListAll(Mockito.anyInt())).willReturn(pageTestData);
        given(musicListMapper.musicListToApiResponse(Mockito.anyList(), Mockito.anyList())).willReturn(pageContentResponse);

        ResultActions actions =
                mockMvc.perform(
                                get(API_PLAYLIST_URL)
                                        .accept(MediaType.APPLICATION_JSON)
                                        .param("member-id",String.valueOf(MemberTestData.MockMember.getMemberData().getMemberId()))
                                        .param("page", String.valueOf(pageTestData.getNumber()+1))

                        )
                        .andExpect(status().isOk())
                        .andDo(
                                MockMvcRestDocumentationWrapper.document("회원의 전체 음악 리스트 조회 기능"
                                        ,preprocessRequest(prettyPrint())
                                        ,preprocessResponse(prettyPrint()),
                                        resource(
                                                ResourceSnippetParameters.builder()
                                                        .description("회원의 전체 음악 리스트 조회 기능 API")
                                                        .requestParameters(
                                                                parameterWithName("member-id").type(SimpleType.NUMBER).description("회원 식별 Id"),
                                                                parameterWithName("page").type(SimpleType.NUMBER).description("가져올 페이지 숫자")
                                                        )
                                                        .responseFields(
                                                                getApiPlayListPageField()
                                                        )
                                                        .build()
                                        )
                                )
                        );




    }

    @Test
    @DisplayName("개인 플레이 리스트 추가(좋아요) 기능 테스트")
    @WithMockUser
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
    @WithMockUser
    @DisplayName("회원의 개인 플레이 리스트 조회(좋아요 리스트)")
    public void getAllMusicListTest() throws Exception{
        Page<PlayList> pageTestData = TestData.ResponseData.PageNationData.getPageData(1,pageContentResponse.size());

        given(musicListService.findPersonalMusicListsPage(Mockito.anyLong(),Mockito.anyInt())).willReturn(pageTestData);
        given(musicListMapper.musicListToPlayListResponseList(Mockito.anyList())).willReturn(pageContentResponse);

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
                                                                getApiPlayListPageField()
                                                        )
                                                        .build()
                                        )
                                )
                        );



    }

    @Test
    @WithMockUser
    @DisplayName("카테고리 태그별 조회 테스트(로그인 된 회원)")
    public void getCategoryAndTagFromMemberTest() throws Exception {

        Page<Music> testPageData = TestData.ResponseData.PageNationData.getPageData(1,pageContentResponse.size()+50);

        given(musicService.findCategoryAndTagsPageMusic(Mockito.any(Music.Category.class),Mockito.anyString(),Mockito.anyInt())).willReturn(testPageData);
        given(memberService.findMember(Mockito.anyLong())).willReturn(MemberTestData.MockMember.getMemberData());
        given(musicListMapper.musicListToApiResponse(Mockito.anyList(),Mockito.anyList())).willReturn(pageContentResponse);

        ResultActions actions =
                mockMvc.perform(
                                get(API_PLAYLIST_GET_URL,"DOGS",1)
                                        .accept(MediaType.APPLICATION_JSON)
                                        .param("page", String.valueOf(testPageData.getNumber()+1))
                                        .param("tags",pageContentResponse.get(0).getCategory())

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
                                                                getApiPlayListPageField()
                                                        )
                                                        .build()
                                        )
                                )
                        );



    }

    @Test
    @WithMockUser
    @DisplayName("개인 플레이 리스트 삭제(좋아요 삭제) 기능 테스트")
    public void deletePersonalPlayListTest() throws Exception {

        MusicDto.DeleteRequest postRequestData = TestData.MockMusic.getDeleteRequestData();
        String content = gson.toJson(postRequestData);

        doNothing().when(musicListService).deletePlayList(Mockito.any(Member.class),Mockito.any(Music.class));


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
