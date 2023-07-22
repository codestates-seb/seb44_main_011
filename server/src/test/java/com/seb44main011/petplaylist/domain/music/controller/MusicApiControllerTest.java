package com.seb44main011.petplaylist.domain.music.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.google.gson.Gson;
import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.member.service.MemberService;
import com.seb44main011.petplaylist.domain.member.stub.MemberTestData;
import com.seb44main011.petplaylist.domain.music.dto.MusicDto;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.mapper.MusicMapper;
import com.seb44main011.petplaylist.domain.music.service.mainService.MusicService;
import com.seb44main011.petplaylist.domain.music.service.storageService.S3Service;
import com.seb44main011.petplaylist.domain.music.stub.TestData;
import com.seb44main011.petplaylist.global.MusicFieldDescriptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public class MusicApiControllerTest extends MusicFieldDescriptor {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;
    @MockBean
    private MusicService service;
    @MockBean
    private MemberService memberService;
    @MockBean
    private MusicMapper mapper;

    private final String API_MUSIC_URL = "/api/musics/{member-id}";
    @Test
    @DisplayName("인증된 사용자의 음악 상세 조회")
    @WithMockUser
    void getPublicMusicFromTitle() throws Exception {
        //given
        MusicDto.ApiResponse response = TestData.MockMusic.getApiResponseData();
        Music mockMusic = TestData.MockMusic.getMusicData();

        given(service.serchMusic(Mockito.anyLong())).willReturn(mockMusic);
        given(service.serchMusic(Mockito.anyString())).willReturn(mockMusic);
        given(memberService.findVerifiedMember(Mockito.anyLong())).willReturn(MemberTestData.MockMember.getMemberData());
        given(mapper.apiResponseToMusic(Mockito.any(Music.class),Mockito.any(Member.class))).willReturn(response);


        ResultActions resultActions1 =
                mockMvc.perform(
                                get(API_MUSIC_URL,1L)
                                        .header(HttpHeaders.AUTHORIZATION,"Bearer accessToken...AnyString...")
                                        .header(HttpHeaders.AUTHORIZATION,"refreshToken...AnyString...")
                                        .param("music_name","testMusicName")
                                        .accept(MediaType.APPLICATION_JSON)

                        )
                        .andExpect(status().isOk())
                        .andDo(
                                MockMvcRestDocumentationWrapper.document("(로그인 상태)사용자의 음악 상세 조회(곡 제목)"
                                        ,preprocessRequest(prettyPrint())
                                        ,preprocessResponse(prettyPrint()),
                                        resource(
                                                ResourceSnippetParameters.builder()
                                                        .description("(로그인 상태)사용자의 음악 상세 조회")
                                                        .requestHeaders(
                                                                getHeaderDescriptorWithTypes()
                                                        )
                                                        .requestParameters(
                                                                parameterWithName("music_name").description("검색을 위한 음악의 이름").optional()
                                                        )
                                                        .responseFields(
                                                                getApiFieldDescriptor()
                                                        )
                                                        .build()
                                        )

                                )

                        );
        ResultActions resultActions2 =
                mockMvc.perform(
                                get(API_MUSIC_URL,1L)
                                        .header(HttpHeaders.AUTHORIZATION,"Bearer accessToken...AnyString...")
                                        .header(HttpHeaders.AUTHORIZATION,"refreshToken...AnyString...")
                                        .param("music_id",String.valueOf(mockMusic.getMusicId()))
                                        .accept(MediaType.APPLICATION_JSON)

                        )
                        .andExpect(status().isOk())
                        .andDo(
                                MockMvcRestDocumentationWrapper.document("(로그인 상태)사용자의 음악 상세 조회(곡 id)"
                                        ,preprocessRequest(prettyPrint())
                                        ,preprocessResponse(prettyPrint()),
                                        resource(
                                                ResourceSnippetParameters.builder()
                                                        .description("(로그인 상태)사용자의 음악 상세 조회")
                                                        .requestHeaders(
                                                                getHeaderDescriptorWithTypes()
                                                        )
                                                        .requestParameters(
                                                                parameterWithName("music_id").description("검색을 위한 음악의 id").optional()
                                                        )
                                                        .responseFields(
                                                                getApiFieldDescriptor()
                                                        )
                                                        .build()
                                        )

                                )

                        );




    }
}
