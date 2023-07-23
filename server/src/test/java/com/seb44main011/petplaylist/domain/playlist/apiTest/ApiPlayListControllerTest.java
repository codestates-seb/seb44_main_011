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
import com.seb44main011.petplaylist.domain.music.stub.TestData;
import com.seb44main011.petplaylist.domain.playlist.dto.PlaylistDto;
import com.seb44main011.petplaylist.domain.playlist.entity.entityTable.PlayList;
import com.seb44main011.petplaylist.domain.playlist.mapper.MusicListMapper;
import com.seb44main011.petplaylist.domain.playlist.service.MusicListService;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.ArrayList;
import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.*;
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
@RunWith(SpringRunner.class)
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
    private final String API_PLAYLIST_URL = "/api/playlist";
    private final String API_PLAYLIST_GET_URL = "/api/playlist/{dogOrCats}/id/{member-id}";
    private static List<PlaylistDto.ApiResponse> pageContentResponse;
    @BeforeEach
    void init(){
        pageContentResponse=TestData.ResponseData.Api.getPlayListResponseList();
    }

    @Test
    @DisplayName("전체 음악 리스트 조회 기능 테스트")
    @WithMockUser
    public void getAllMusicListFromMemberTest() throws Exception {
        Page<Music> pageTestData = TestData.ResponseData.PageNationData.getPageData(1,pageContentResponse.size());

        given(musicListService.findPersonalMusicLists(Mockito.anyLong())).willReturn(List.of(TestData.MockMusicList.getMusicListData()));
        given(musicService.findMusicListAll(Mockito.anyInt(),Mockito.anyString())).willReturn(pageTestData);
        given(musicListMapper.musicListToApiResponse(Mockito.anyList(), Mockito.anyList())).willReturn(pageContentResponse);

        ResultActions actions =
                mockMvc.perform(
                                get(API_PLAYLIST_URL)
                                        .accept(MediaType.APPLICATION_JSON)
                                        .param("member-id",String.valueOf(MemberTestData.MockMember.getMemberData().getMemberId()))
                                        .param("page", String.valueOf(pageTestData.getNumber()+1))
                                        .param("sort", "new")
                                        .header(HttpHeaders.AUTHORIZATION,"Bearer accessToken...AnyString...")
                                        .header(HttpHeaders.AUTHORIZATION,"refreshToken...AnyString...")

                        )
                        .andExpect(status().isOk())
                        .andDo(
                                MockMvcRestDocumentationWrapper.document("회원의 전체 음악 리스트 조회 기능"
                                        ,preprocessRequest(prettyPrint())
                                        ,preprocessResponse(prettyPrint()),
                                        resource(
                                                ResourceSnippetParameters.builder()
                                                        .description("회원의 전체 음악 리스트 조회 기능 API")
                                                        .requestHeaders(
                                                                getHeaderDescriptorWithTypes()
                                                        )
                                                        .requestParameters(
                                                                parameterWithName("member-id").type(SimpleType.NUMBER).description("회원 식별 Id"),
                                                                parameterWithName("page").type(SimpleType.NUMBER).description("가져올 페이지 숫자"),
                                                                parameterWithName("sort").type(SimpleType.STRING).description("정렬기준(new,old)").optional()
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
                                .header(HttpHeaders.AUTHORIZATION,"Bearer accessToken...AnyString...")
                                .header(HttpHeaders.AUTHORIZATION,"refreshToken...AnyString...")
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
                                                        .requestHeaders(
                                                                getHeaderDescriptorWithTypes()
                                                        )
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

        given(musicListService.findPersonalMusicListsPage(Mockito.anyString(),Mockito.anyLong(),Mockito.anyInt())).willReturn(pageTestData);
        given(musicListMapper.musicListToPlayListResponseList(Mockito.anyList())).willReturn(pageContentResponse);

        ResultActions actions =
                mockMvc.perform(
                                get(API_PLAYLIST_URL+"/{member-id}",1)
                                        .accept(MediaType.APPLICATION_JSON)
                                        .param("page", String.valueOf(pageTestData.getNumber()+1))
                                        .header(HttpHeaders.AUTHORIZATION,"Bearer accessToken...AnyString...")
                                        .header(HttpHeaders.AUTHORIZATION,"refreshToken...AnyString...")

                        )
                        .andExpect(status().isOk())
                        .andDo(
                                MockMvcRestDocumentationWrapper.document("회원의 개인 플레이 리스트 조회(좋아요 리스트)"
                                        ,preprocessRequest(prettyPrint())
                                        ,preprocessResponse(prettyPrint()),
                                        resource(
                                                ResourceSnippetParameters.builder()
                                                        .description("개인 플레이 리스트 조회(좋아요 리스트) API")
                                                        .requestHeaders(
                                                                getHeaderDescriptorWithTypes()
                                                        )
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
        List<PlaylistDto.ApiResponse> apiResponses = TestData.ResponseData.Api.getPlayListResponseList();


        given(musicService.findCategoryAndTagsPageMusic(Mockito.any(Music.Category.class),Mockito.anyString(),Mockito.anyInt(),Mockito.anyString())).willReturn(testPageData);
        given(musicListService.findPersonalMusicLists(Mockito.anyLong())).willReturn(new ArrayList<>());
        given(musicListMapper.musicListToApiResponse(Mockito.anyList(),Mockito.anyList())).willReturn(apiResponses);

        ResultActions actions =
                mockMvc.perform(
                                get(API_PLAYLIST_GET_URL,"DOGS",1)
                                        .accept(MediaType.APPLICATION_JSON)
                                        .param("page", String.valueOf(testPageData.getNumber()+1))
                                        .param("tags",apiResponses.get(0).getTags())
                                        .param("sort","new")
                                        .header(HttpHeaders.AUTHORIZATION,"Bearer accessToken...AnyString...")
                                        .header(HttpHeaders.AUTHORIZATION,"refreshToken...AnyString...")

                        )
                        .andExpect(status().isOk())
                        .andDo(
                                MockMvcRestDocumentationWrapper.document("카테고리 테크별 조회 API(로그인 된 회원)"
                                        ,preprocessRequest(prettyPrint())
                                        ,preprocessResponse(prettyPrint()),
                                        resource(
                                                ResourceSnippetParameters.builder()
                                                        .description("로그인 상태인 회원의 카테고리 테크별 조회 API")
                                                        .requestHeaders(
                                                                getHeaderDescriptorWithTypes()
                                                        )
                                                        .pathParameters(
                                                                parameterWithName("dogOrCats").type(SimpleType.STRING).description("카테고리(DOGS,CATS)"),
                                                                parameterWithName("member-id").type(SimpleType.NUMBER).description("회원 식별 Id")

                                                        )
                                                        .requestParameters(
                                                                parameterWithName("page").type(SimpleType.NUMBER).description("가져올 페이지 숫자"),
                                                                parameterWithName("tags").type(SimpleType.STRING).description("조회 할 태그").optional(),
                                                                parameterWithName("sort").type(SimpleType.STRING).description("정렬 순서").optional()
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
                                        .header(HttpHeaders.AUTHORIZATION,"Bearer accessToken...AnyString...")
                                        .header(HttpHeaders.AUTHORIZATION,"refreshToken...AnyString...")
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
                                                        .requestHeaders(
                                                                getHeaderDescriptorWithTypes()
                                                        )
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
    @DisplayName("회원의 타이틀 검색시 뮤직리스트 응답 테스트")
    @WithMockUser
    public void getSearchTitleMusicListFromNonMemberTest() throws Exception {
        List<PlaylistDto.ApiResponse> apiResponsesTestData = TestData.ResponseData.Api.getPlayListResponseList();
        given(musicService.findMusicListFromTitle(Mockito.anyString(),Mockito.anyInt(),Mockito.anyString())).willReturn(TestData.ResponseData.PageNationData.getPageData(1,apiResponsesTestData.size()));
        given(musicListService.findPersonalMusicLists(Mockito.anyLong())).willReturn(new ArrayList<>());
        given(musicListMapper.musicListToApiResponse(Mockito.anyList(),Mockito.anyList())).willReturn(apiResponsesTestData);

        ResultActions actions =
                mockMvc.perform(
                                get(API_PLAYLIST_URL+"/search")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .param("title","노래")
                                        .param("page","1")
                                        .param("sort","old")
                                        .header(HttpHeaders.AUTHORIZATION,"Bearer accessToken...AnyString...")
                                        .header(HttpHeaders.AUTHORIZATION,"refreshToken...AnyString...")

                        )
                        .andExpect(status().isOk())
                        .andDo(
                                MockMvcRestDocumentationWrapper.document("타이틀 음악 리스트 검색 기능"
                                        ,preprocessRequest(prettyPrint())
                                        ,preprocessResponse(prettyPrint()),
                                        resource(
                                                ResourceSnippetParameters.builder()
                                                        .description("로그인 상태 시 타이틀 음악 리스트 조회 기능 API")
                                                        .requestHeaders(
                                                                getHeaderDescriptorWithTypes()
                                                        )
                                                        .requestParameters(
                                                                parameterWithName("title").type(SimpleType.STRING).description("검색할 이름"),
                                                                parameterWithName("page").type(SimpleType.STRING).description("검색할 현재 페이지").optional(),
                                                                parameterWithName("sort").type(SimpleType.STRING).description("정렬순서 (new, old)").optional()
                                                        )
                                                        .responseFields(
                                                                getApiPlayListPageField()
                                                        )
                                                        .build()
                                        )
                                )
                        );
    }



}
