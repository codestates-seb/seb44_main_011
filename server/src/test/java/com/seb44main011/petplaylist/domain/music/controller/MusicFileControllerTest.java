package com.seb44main011.petplaylist.domain.music.controller;


import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.member.stub.MemberTestData;
import com.seb44main011.petplaylist.domain.music.dto.MusicDto;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.mapper.MusicMapper;
import com.seb44main011.petplaylist.domain.music.service.mainService.MusicService;
import com.seb44main011.petplaylist.domain.music.stub.TestData;
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
import org.springframework.mock.web.MockPart;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Test
    @DisplayName("음악 파일 S3 저장 기능 테스트")
    void postMusicFileTest() throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //given
        Music testMusic = TestData.MockMusic.getMusicData();
        String fileContent = gson.toJson(TestData.MockMusic.getPostMusicFile());
        MusicDto.PublicResponse response = TestData.MockMusic.getPublicResponseData();
        MockMultipartFile multipartFile = new MockMultipartFile("musicInfo","musicInfo","application/json",fileContent.getBytes(StandardCharsets.UTF_8));

        //when
        given(musicService.uploadMusic(Mockito.anyList(),Mockito.any(MusicDto.PostMusicFile.class))).willReturn(testMusic);
        given(musicMapper.publicResponseToMusic(Mockito.any(Music.class))).willReturn(response);


        //then
        ResultActions resultActions1 =
                mockMvc.perform(
                                multipart(ADMIN_URL)
//                                        .part(new MockPart("img",TestData.MockMusicFile.getImgMultipartFile().getBytes()))
//                                        .part(new MockPart("mp3",TestData.MockMusicFile.getMp3MultipartFile().getBytes()))
//                                        .part(new MockPart("request", fileContent.getBytes(StandardCharsets.UTF_8)))
                                        .file(TestData.MockMusicFile.getImgMultipartFile())
                                        .file(TestData.MockMusicFile.getMp3MultipartFile())
                                        .file(multipartFile)
                                        .contentType("multipart/form-data")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .characterEncoding("UTF-8")

                        )
                        .andExpect(status().isCreated())
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

}



//.document(
//        resource(
//        ResourceSnippetParameters.builder()
//        .description("관리자 권한 유저의 음악 파일 S3 저장 기능")
////                                                                .requestParameters(
////                                                                        parameterWithName("img").description("음악 이미지 파일(png,jpg,jpeg)"),
////                                                                        parameterWithName("mp3").description("음악 mp3 파일"),
////                                                                        parameterWithName("request").description("음악 데이터 정보")
////                                                                    )
//        .responseFields(
//        fieldWithPath("musicId").type(JsonFieldType.NUMBER).description("음악 식별 Id"),
//        fieldWithPath("title").type(JsonFieldType.STRING).description("음악 타이틀"),
//        fieldWithPath("music_url").type(JsonFieldType.STRING).description("음악 mp3 파일 url"),
//        fieldWithPath("image_url").type(JsonFieldType.STRING).description("음악 img 파일 url"),
//        fieldWithPath("playtime").type(JsonFieldType.STRING).description("음악 재생 시간"),
//        fieldWithPath("tags").type(JsonFieldType.STRING).description("음악 태그")
//        )
//        .build()
//        )
//
//        )
