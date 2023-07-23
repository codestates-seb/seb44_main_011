package com.seb44main011.petplaylist.domain.playlist.publicTest;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.google.gson.Gson;
import com.seb44main011.petplaylist.domain.member.repository.MemberRepository;
import com.seb44main011.petplaylist.domain.member.service.MemberService;
import com.seb44main011.petplaylist.domain.member.stub.MemberTestData;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.repository.MusicRepository;
import com.seb44main011.petplaylist.domain.music.service.mainService.MusicService;
import com.seb44main011.petplaylist.domain.music.service.storageService.S3Service;
import com.seb44main011.petplaylist.domain.music.stub.TestData;
import com.seb44main011.petplaylist.domain.playlist.dto.PlaylistDto;
import com.seb44main011.petplaylist.domain.playlist.mapper.MusicListMapper;
import com.seb44main011.petplaylist.domain.playlist.service.MusicListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.List;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class PublicPlayListControllerTest extends PublicFieldDescriptor{
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MemberService memberService;
    @MockBean
    private MusicService musicService;
    @MockBean
    private MusicListMapper musicListMapper;



    private final String PUBLIC_PLAYLIST_URL = "/public/playlist";

    List<PlaylistDto.PublicResponse> publicResponseList;
    @BeforeEach
    void init(){
        this.publicResponseList = TestData.ResponseData.Public.getPublicCategoryPlayListResponseList();
    }

    @Test
    @DisplayName("비회원 전체 플레이 리스트 조회 테스트")
    @WithMockUser
    public void getAllMusicListTest() throws Exception{
        Page<Music> testPageData = TestData.ResponseData.PageNationData.getPageData(2,publicResponseList.size()+6);

        given(musicService.findMusicListAll(Mockito.anyInt(),Mockito.anyString())).willReturn(testPageData);
        given(musicListMapper.musicListToPublicResponse(Mockito.anyList())).willReturn(publicResponseList);


        ResultActions actions =
                mockMvc.perform(
                                get(PUBLIC_PLAYLIST_URL)
                                        .accept(MediaType.APPLICATION_JSON)
                                        .param("page", String.valueOf(testPageData.getNumber()+1))
                                        .param("sort", "new")

                        )
                        .andExpect(status().isOk())
                        .andDo(
                                MockMvcRestDocumentationWrapper.document("전체 플레이 리스트 조회 API(비 로그인)"
                                        ,preprocessRequest(prettyPrint())
                                        ,preprocessResponse(prettyPrint()),
                                        resource(
                                                ResourceSnippetParameters.builder()
                                                        .description("비 로그인 상태 시 전체 플레이 리스트 조회 API")
                                                        .requestParameters(
                                                                parameterWithName("page").type(SimpleType.NUMBER).description("가져올 페이지 숫자"),
                                                                parameterWithName("sort").type(SimpleType.STRING).description("정렬 기준(new,old)").optional()
                                                        )
                                                        .responseFields(
                                                                getPublicPlayListPage()
                                                        )
                                                        .build()
                                        )
                                )
                        );

    }

    @Test
    @DisplayName("카테고리 태그별 조회 테스트(비 로그인)")
    @WithMockUser
    public void getCategoryAndTagFromMemberTest() throws Exception {

        Page<Music> testPageData = TestData.ResponseData.PageNationData.getPageData(2,publicResponseList.size()+46);

        given(musicService.findCategoryAndTagsPageMusic(Mockito.any(Music.Category.class),Mockito.anyString(),Mockito.anyInt(),Mockito.anyString())).willReturn(testPageData);
        given(memberService.findMember(Mockito.anyLong())).willReturn(MemberTestData.MockMember.getMemberData());
        given(musicListMapper.musicListToPublicResponse(Mockito.anyList())).willReturn(publicResponseList);

        ResultActions actions =
                mockMvc.perform(
                                get(PUBLIC_PLAYLIST_URL+"/{dogOrCats}",publicResponseList.get(0).getCategory())
                                        .accept(MediaType.APPLICATION_JSON)
                                        .param("page", String.valueOf(testPageData.getNumber()+1))
                                        .param("tags",publicResponseList.get(0).getCategory())
                                        .param("sort","view")

                        )
                        .andExpect(status().isOk())
                        .andDo(
                                MockMvcRestDocumentationWrapper.document("카테고리 테크별 조회 API(비 로그인)"
                                        ,preprocessRequest(prettyPrint())
                                        ,preprocessResponse(prettyPrint()),
                                        resource(
                                                ResourceSnippetParameters.builder()
                                                        .description("비 로그인 상태 시 카테고리 테크별 조회 API")
                                                        .pathParameters(
                                                                parameterWithName("dogOrCats").type(SimpleType.STRING).description("카테고리(DOGS,CATS)")
                                                        )
                                                        .requestParameters(
                                                                parameterWithName("page").type(SimpleType.NUMBER).description("가져올 페이지 숫자"),
                                                                parameterWithName("tags").type(SimpleType.STRING).description("조회 할 태그").optional(),
                                                                parameterWithName("sort").type(SimpleType.STRING).description("정렬순서 (new, old)").optional()
                                                        )
                                                        .responseFields(
                                                                getPublicPlayListPage()
                                                        )
                                                        .build()
                                        )
                                )
                        );



    }

    @Test
    @DisplayName("비회원의 타이틀 검색시 뮤직리스트 응답 테스트")
    public void getSearchTitleMusicListFromNonMemberTest() throws Exception {
        List<PlaylistDto.PublicResponse> responsesTestData = TestData.ResponseData.Public.getPublicCategoryPlayListResponseList();
        given(musicService.findMusicListFromTitle(Mockito.anyString(),Mockito.anyInt(),Mockito.anyString())).willReturn(TestData.ResponseData.PageNationData.getPageData(1,responsesTestData.size()));
        given(musicListMapper.musicListToPublicResponse(Mockito.anyList())).willReturn(responsesTestData);

        ResultActions actions =
                mockMvc.perform(
                                get(PUBLIC_PLAYLIST_URL+"/search")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .param("title","곡")
                                        .param("page","1")
                                        .param("sort","old")

                        )
                        .andExpect(status().isOk())
                        .andDo(
                                MockMvcRestDocumentationWrapper.document("타이틀 음악 리스트 검색 기능"
                                        ,preprocessRequest(prettyPrint())
                                        ,preprocessResponse(prettyPrint()),
                                        resource(
                                                ResourceSnippetParameters.builder()
                                                        .description("비 로그인 상태 시 타이틀 음악 리스트 조회 기능 API")
                                                        .requestParameters(
                                                                parameterWithName("title").type(SimpleType.STRING).description("검색할 이름"),
                                                                parameterWithName("page").type(SimpleType.STRING).description("검색할 현재 페이지").optional(),
                                                                parameterWithName("sort").type(SimpleType.STRING).description("정렬순서 (new, old)").optional()
                                                        )
                                                        .responseFields(
                                                                getPublicPlayListPage()
                                                        )
                                                        .build()
                                        )
                                )
                        );
    }


}
