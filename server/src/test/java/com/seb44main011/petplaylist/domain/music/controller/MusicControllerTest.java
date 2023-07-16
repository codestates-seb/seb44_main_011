package com.seb44main011.petplaylist.domain.music.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.google.gson.Gson;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public class MusicControllerTest extends MusicFieldDescriptor {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;
    @MockBean
    private MusicService service;
    @MockBean
    private MusicMapper mapper;

    private final String PUBLIC_MUSIC_URL = "/public/musics";

    @Test
    @DisplayName("인증되지 않은 사용자의 음악 상세 조회")
    @WithMockUser
    void getPublicMusicFromTitle() throws Exception {
        //given
        MusicDto.PublicResponse response = TestData.MockMusic.getPublicResponseData();
        Music mockMusic = TestData.MockMusic.getMusicData();
        String content = gson.toJson(response);

        given(service.serchMusic(Mockito.anyLong())).willReturn(mockMusic);
        given(service.serchMusic(Mockito.anyString())).willReturn(mockMusic);
        given(mapper.publicResponseToMusic(Mockito.any(Music.class))).willReturn(response);


        ResultActions resultActions1 =
                mockMvc.perform(
                        get(PUBLIC_MUSIC_URL)
                                .param("music_name","testMusicName")
                                .accept(MediaType.APPLICATION_JSON)

                )
                        .andExpect(status().isOk())
                        .andDo(
                                MockMvcRestDocumentationWrapper.document("인증되지 않은 사용자의 음악 상세 조회(곡 제목)"
                                        ,preprocessRequest(prettyPrint())
                                        ,preprocessResponse(prettyPrint()),
                                        resource(
                                                ResourceSnippetParameters.builder()
                                                        .description("인증되지 않은 사용자의 음악 상세 조회")
                                                        .requestParameters(
                                                                parameterWithName("music_name").description("검색을 위한 음악의 이름").optional()
                                                        )
                                                        .responseFields(
                                                               getPublicFieldDescriptor()
                                                        )
                                                        .build()
                                        )

                                        )

                        );
        ResultActions resultActions2 =
                mockMvc.perform(
                        get(PUBLIC_MUSIC_URL)
                                .param("music_id",String.valueOf(mockMusic.getMusicId()))
                                .accept(MediaType.APPLICATION_JSON)

                )
                        .andExpect(status().isOk())
                        .andDo(
                            MockMvcRestDocumentationWrapper.document("인증되지 않은 사용자의 음악 상세 조회(곡 id)"
                                    ,preprocessRequest(prettyPrint())
                                    ,preprocessResponse(prettyPrint()),
                                    resource(
                                            ResourceSnippetParameters.builder()
                                                    .description("인증되지 않은 사용자의 음악 상세 조회")
                                                    .requestParameters(
                                                            parameterWithName("music_id").description("검색을 위한 음악의 id").optional()
                                                 )
                                                    .responseFields(
                                                            getPublicFieldDescriptor()
                                                    )
                                                    .build()
                                    )

                         )

                 );




    }



}
