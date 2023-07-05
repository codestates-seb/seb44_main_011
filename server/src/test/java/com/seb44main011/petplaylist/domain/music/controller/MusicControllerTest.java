package com.seb44main011.petplaylist.domain.music.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.google.gson.Gson;
import com.seb44main011.petplaylist.domain.music.dto.MusicDto;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.mapper.MusicMapper;
import com.seb44main011.petplaylist.domain.music.service.MusicService;
import com.seb44main011.petplaylist.domain.music.stub.MusicTestData;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MusicControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;
    @MockBean
    private MusicService service;
    @MockBean
    private MusicMapper mapper;

    private final String PUBLIC_MUSIC_URL = "/public/musics";
//    @BeforeEach
//    void init(){
//
//    }

    @Test
    @DisplayName("인증되지 않은 사용자의 음악 상세 조회")
    void getPublicMusic() throws Exception {
        //given
        MusicDto.PublicResponse response = MusicTestData.MockMusic.getPublicResponseData();
        Music mockMusic = MusicTestData.MockMusic.getMusicData();
        String content = gson.toJson(response);

        given(service.findMusic(Mockito.anyString())).willReturn(mockMusic);
        given(mapper.publicResponseToMusic(Mockito.any(Music.class))).willReturn(response);


        ResultActions actions =
                mockMvc.perform(
                        get(PUBLIC_MUSIC_URL)
                                .param("music_name","testMusicName")
                                .accept(MediaType.APPLICATION_JSON)

                )
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.musicId").value(1L))
                        .andDo(
                                MockMvcRestDocumentationWrapper.document("인증되지 않은 사용자의 음악 상세 조회(곡 제목)"
                                        ,preprocessRequest(prettyPrint())
                                        ,preprocessResponse(prettyPrint()),
                                        resource(
                                                ResourceSnippetParameters.builder()
                                                        .description("인증되지 않은 사용자의 음악 상세 조회")
                                                        .requestParameters(
                                                                parameterWithName("music_name").description("검색을 위한 음악의 이름")
                                                        )
                                                        .responseFields(
                                                                fieldWithPath("musicId").type(JsonFieldType.NUMBER).description("회원 식별 Id"),
                                                                fieldWithPath("title").type(JsonFieldType.STRING).description("곡 이름"),
                                                                fieldWithPath("music_url").type(JsonFieldType.STRING).description("곡 url"),
                                                                fieldWithPath("image_url").type(JsonFieldType.STRING).description("이미지 url"),
                                                                fieldWithPath("tags").type(JsonFieldType.STRING).description("곡의 태그"))
                                                        .build()
                                        )

                                        )

                        );
    }



}
