package com.seb44main011.petplaylist.domain.music.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.seb44main011.petplaylist.domain.member.auth.jwt.JwtTokenizer;
import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.member.service.MemberService;
import com.seb44main011.petplaylist.domain.member.stub.MemberTestData;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.mapper.MusicMapper;
import com.seb44main011.petplaylist.domain.music.service.mainService.MusicService;
import com.seb44main011.petplaylist.domain.music.stub.TestData;
import com.seb44main011.petplaylist.global.MusicFieldDescriptor;
import com.seb44main011.petplaylist.global.SecurityWithMockUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public class MusicAdminControllerTest extends MusicFieldDescriptor {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MusicMapper musicMapper;

    @MockBean
    private MusicService musicService;
    @MockBean
    private MemberService memberService;

    private final String ADMIN_URL="/admin/music";
    private String accessToken;

    @Autowired
    private JwtTokenizer jwtTokenizer;
    @BeforeEach
    public void init(){
        accessToken = SecurityWithMockUser.getValidAccessToken(jwtTokenizer.getSecretKeyString(),"ADMIN");
    }


    @Test
    @DisplayName("관리자 권한 음악 상세 조회 기능 테스트")
    void adminGetMusicByIdTest() throws Exception {
        Member testMember = MemberTestData.MockMember.getMemberData();
        Music testMusic =TestData.MockMusic.getMusicData();

        //when
        given(memberService.findByMemberFromEmail(Mockito.anyString())).willReturn(testMember);
        given(musicService.findMusicAnyStatus(Mockito.anyLong())).willReturn(testMusic);
        given(musicMapper.musicToAdminResponse(Mockito.any(Music.class))).willReturn(TestData.ResponseData.Admin.getAdminResponse());

        assertEquals(testMusic.getStatus(),Music.Status.ACTIVE);
        //then
        ResultActions resultActions1 =
                mockMvc.perform(
                        get(ADMIN_URL.concat("/id/{music-Id}"),1L)
                                .header("Authorization","Bearer "+SecurityWithMockUser.getValidAccessToken(jwtTokenizer.getSecretKeyString(),"ADMIN")))
                        .andExpect(status().isOk())
                        .andDo(
                                MockMvcRestDocumentationWrapper
                                        .document("음악 상세 조회(id)(admin)"
                                                ,preprocessRequest(prettyPrint())
                                                ,preprocessResponse(prettyPrint())
                                                ,pathParameters(
                                                        parameterWithName("music-Id").description("음악 식별 Id")
                                                )
                                                ,responseFields(
                                                        getAdminDescriptor()
                                                )



                                        )
                        );


    }

    @Test
    @DisplayName("관리자 권한 음악 상세 조회 기능 테스트")
    void adminGetMusicByTitleTest() throws Exception {
        Member testMember = MemberTestData.MockMember.getMemberData();
        Music testMusic =TestData.MockMusic.getMusicData();

        //when
        given(memberService.findByMemberFromEmail(Mockito.anyString())).willReturn(testMember);
        given(musicService.findMusicAnyStatus(Mockito.anyString())).willReturn(testMusic);
        given(musicMapper.musicToAdminResponse(Mockito.any(Music.class))).willReturn(TestData.ResponseData.Admin.getAdminResponse());

        assertEquals(testMusic.getStatus(),Music.Status.ACTIVE);
        //then
        ResultActions resultActions1 =
                mockMvc.perform(
                                get(ADMIN_URL.concat("/title/{title}"),testMusic.getTitle())
                                        .accept(MediaType.APPLICATION_JSON)
                                        .header("Authorization","Bearer "+SecurityWithMockUser.getValidAccessToken(jwtTokenizer.getSecretKeyString(),"ADMIN")))
                        .andExpect(status().isOk())
                        .andDo(
                                MockMvcRestDocumentationWrapper
                                        .document("상세 음악 조회(title)(admin)"
                                                ,preprocessRequest(prettyPrint())
                                                ,preprocessResponse(prettyPrint())
                                                ,pathParameters(
                                                        parameterWithName("title").description("음악 title")
                                                )
                                                ,responseFields(
                                                        getAdminDescriptor()
                                                )



                                        )
                        );


    }
    @Test
    @DisplayName("관리자 권한 모든 음악 조회 기능 테스트")
    void adminGetAllMusicListTest() throws Exception {
        Member testMember = MemberTestData.MockMember.getMemberData();
        Page<Music> page = TestData.ResponseData.PageNationData.getPageData(1,3);

        //when
        given(memberService.findByMemberFromEmail(Mockito.anyString())).willReturn(testMember);
        given(musicService.findMusicListAllFromAdmin(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString())).willReturn(page);
        given(musicMapper.musicToAdminResponseList(Mockito.anyList())).willReturn(TestData.ResponseData.Admin.getAdminResponseList());

        //then
        ResultActions resultActions1 =
                mockMvc.perform(
                                get(ADMIN_URL.concat("/all"))
                                        .param("page", "1")
                                        .param("size","6")
                                        .param("sort","OLD")
                                        .header("Authorization","Bearer "+SecurityWithMockUser.getValidAccessToken(jwtTokenizer.getSecretKeyString(),"ADMIN")))
                        .andExpect(status().isOk())
                        .andDo(
                                MockMvcRestDocumentationWrapper
                                        .document("상태값 상관이 없이 전체 음악에서 상세음악 조회(admin)"
                                                ,preprocessRequest(prettyPrint())
                                                ,preprocessResponse(prettyPrint())
                                                ,requestParameters(
                                                        parameterWithName("page").description("표시할 페이지(기본 값 1)").optional(),
                                                        parameterWithName("size").description("한 페이지당 데이터 수(기본 값 6)").optional(),
                                                        parameterWithName("sort").description("정렬 기준[new,old](없을 시 조회수 순)").optional()
                                                )
                                                ,responseFields(
                                                        getAdminDescriptorList()
                                                )



                                        )
                        );


    }

    @Test
    @DisplayName("관리자 권한 모든 활성화 음악 조회 기능 테스트")
    void adminGetAllActiveMusicListTest() throws Exception {
        Member testMember = MemberTestData.MockMember.getMemberData();
        Page<Music> page = TestData.ResponseData.PageNationData.getPageData(1,3);

        //when
        given(memberService.findByMemberFromEmail(Mockito.anyString())).willReturn(testMember);
        given(musicService.findMusicListAllFromAdminByStatus(Mockito.any(Music.Status.class),Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString())).willReturn(page);
        given(musicMapper.musicToAdminResponseList(Mockito.anyList())).willReturn(TestData.ResponseData.Admin.getAdminActiveResponseList());

        //then
        ResultActions resultActions1 =
                mockMvc.perform(
                                get(ADMIN_URL.concat("/active"))
                                        .param("page", "1")
                                        .param("size","6")
                                        .param("sort","OLD")
                                        .header("Authorization","Bearer "+SecurityWithMockUser.getValidAccessToken(jwtTokenizer.getSecretKeyString(),"ADMIN")))
                        .andExpect(status().isOk())
                        .andDo(
                                MockMvcRestDocumentationWrapper
                                        .document("모든 활성화 상태(Active) 음악 조회(admin)"
                                                ,preprocessRequest(prettyPrint())
                                                ,preprocessResponse(prettyPrint())
                                                ,requestParameters(
                                                        parameterWithName("page").description("표시할 페이지(기본 값 1)").optional(),
                                                        parameterWithName("size").description("한 페이지당 데이터 수(기본 값 6)").optional(),
                                                        parameterWithName("sort").description("정렬 기준[new,old](없을 시 조회수 순)").optional()
                                                )
                                                ,responseFields(
                                                        getAdminDescriptorList()
                                                )



                                        )
                        );


    }

    @Test
    @DisplayName("관리자 권한 모든 비 활성화 음악 조회 기능 테스트")
    void adminGetAllInactiveMusicListTest() throws Exception {
        Member testMember = MemberTestData.MockMember.getMemberData();
        Page<Music> page = TestData.ResponseData.PageNationData.getPageData(1,3);

        //when
        given(memberService.findByMemberFromEmail(Mockito.anyString())).willReturn(testMember);
        given(musicService.findMusicListAllFromAdminByStatus(Mockito.any(Music.Status.class),Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString())).willReturn(page);
        given(musicMapper.musicToAdminResponseList(Mockito.anyList())).willReturn(TestData.ResponseData.Admin.getAdminInactiveResponseList());

        //then
        ResultActions resultActions1 =
                mockMvc.perform(
                                get(ADMIN_URL.concat("/active"))
                                        .param("page", "1")
                                        .param("size","6")
                                        .param("sort","OLD")
                                        .header("Authorization","Bearer "+SecurityWithMockUser.getValidAccessToken(jwtTokenizer.getSecretKeyString(),"ADMIN")))
                        .andExpect(status().isOk())
                        .andDo(
                                MockMvcRestDocumentationWrapper
                                        .document("모든 비 활성화 상태(Inactive) 음악 조회(admin)"
                                                ,preprocessRequest(prettyPrint())
                                                ,preprocessResponse(prettyPrint())
                                                ,requestParameters(
                                                        parameterWithName("page").description("표시할 페이지(기본 값 1)").optional(),
                                                        parameterWithName("size").description("한 페이지당 데이터 수(기본 값 6)").optional(),
                                                        parameterWithName("sort").description("정렬 기준[new,old](없을 시 조회수 순)").optional()
                                                )
                                                ,responseFields(
                                                        getAdminDescriptorList()
                                                )



                                        )
                        );


    }
}
