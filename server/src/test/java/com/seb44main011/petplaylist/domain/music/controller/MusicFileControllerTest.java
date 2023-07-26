package com.seb44main011.petplaylist.domain.music.controller;


import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.seb44main011.petplaylist.domain.member.auth.jwt.JwtTokenizer;
import com.seb44main011.petplaylist.domain.music.dto.MusicDto;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.mapper.MusicMapper;
import com.seb44main011.petplaylist.domain.music.service.mainService.MusicService;
import com.seb44main011.petplaylist.domain.music.stub.TestData;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public class MusicFileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MusicMapper musicMapper;

    @MockBean
    private MusicService musicService;

    private final String ADMIN_URL="/admin/music";

    private  static String accessKey;
    @Autowired
    private JwtTokenizer jwtTokenizer;
    @BeforeEach
    public void init(){
        accessKey = SecurityWithMockUser.getValidAccessToken(jwtTokenizer.getSecretKeyString(),"ADMIN");
    }

    @Test
    @DisplayName("음악 파일 S3 저장 기능 테스트")
    void postMusicFileTest() throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //given
        Music testMusic = TestData.MockMusic.getMusicData();
        String fileContent = gson.toJson(TestData.MockMusic.getPostMusicFile());
        MusicDto.PublicResponse response = TestData.MockMusic.getPublicResponseData();
        String content = gson.toJson(response);
        MockMultipartFile multipartFile = new MockMultipartFile("musicInfo","musicInfo","application/json",fileContent.getBytes(StandardCharsets.UTF_8));

        //when
        given(musicService.uploadMusic(Mockito.anyList(),Mockito.any(MusicDto.PostMusicFile.class))).willReturn(testMusic);
        given(musicMapper.publicResponseToMusic(Mockito.any(Music.class))).willReturn(response);


        //then
        ResultActions resultActions1 =
                mockMvc.perform(
                                multipart(ADMIN_URL)
                                        .file(TestData.MockMusicFile.getImgMultipartFile())
                                        .file(TestData.MockMusicFile.getMp3MultipartFile())
                                        .file(multipartFile)
                                        .header("Authorization","Bearer ".concat(accessKey))
                                        .contentType("multipart/form-data")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .characterEncoding("UTF-8")

                        )
                        .andExpect(status().isCreated())
                        .andExpect(content().json(content))
                        .andDo(
                                MockMvcRestDocumentationWrapper
                                        .document("음악 파일 S3 저장 기능(admin)"
                                                ,preprocessRequest(prettyPrint())
                                                ,preprocessResponse(prettyPrint())
                                                ,responseFields(
                                                        fieldWithPath("musicId").type(JsonFieldType.NUMBER).description("음악 식별 Id"),
                                                        fieldWithPath("title").type(JsonFieldType.STRING).description("음악 타이틀"),
                                                        fieldWithPath("music_url").type(JsonFieldType.STRING).description("음악 mp3 파일 url"),
                                                        fieldWithPath("image_url").type(JsonFieldType.STRING).description("음악 img 파일 url"),
                                                        fieldWithPath("playtime").type(JsonFieldType.STRING).description("음악 재생 시간"),
                                                        fieldWithPath("tags").type(JsonFieldType.STRING).description("음악 태그")
                                                )
                                                ,requestParts(
                                                        partWithName("img").description("음악 이미지 파일(png,jpg,jpeg)"),
                                                        partWithName("mp3").description("음악 mp3 파일"),
                                                        partWithName("musicInfo").description("음악 데이터 정보(JSON)")
                                                ),requestPartBody(
                                                        "musicInfo"
                                                )

                                        )
                        );


    }

    @Test
    @DisplayName("음원 파일 비 활성화 테스트")
    @WithMockUser
    void deleteMusicFileTest() throws Exception {
        //when
        doNothing().when(musicService).deleteMusicFile(Mockito.anyLong());


        //then
        ResultActions resultActions1 =
                mockMvc.perform(
                        delete(ADMIN_URL.concat("/id/{music-Id}"),1L)
                                .header("Authorization","Bearer ".concat(accessKey))

                        )
                        .andExpect(status().isNoContent())
                        .andDo(
                                MockMvcRestDocumentationWrapper
                                        .document("음원 파일 비활성화 기능(admin)"
                                                ,preprocessRequest(prettyPrint())
                                                ,preprocessResponse(prettyPrint())
                                                ,pathParameters(
                                                        parameterWithName("music-Id").description("음악 식별 Id")
                                                )


                                        )
                        );


    }

    @Test
    @DisplayName("음원 파일 재 활성화 기능 테스트")
    @WithMockUser
    void revertMusicFileTest() throws Exception {
        //when
        doNothing().when(musicService).revertMusicFile(Mockito.anyLong());


        //then
        ResultActions resultActions1 =
                mockMvc.perform(
                        patch(ADMIN_URL.concat("/id/{music-Id}"),1L)
                                .header("Authorization","Bearer ".concat(accessKey))
                        )
                        .andExpect(status().isOk())
                        .andDo(
                                MockMvcRestDocumentationWrapper
                                        .document("음원 파일 재 활성화 기능(admin)"
                                                ,preprocessRequest(prettyPrint())
                                                ,preprocessResponse(prettyPrint())
                                                ,pathParameters(
                                                        parameterWithName("music-Id").description("음악 식별 Id")
                                                )


                                        )
                        );


    }

}

