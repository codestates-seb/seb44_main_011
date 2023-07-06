package com.seb44main011.petplaylist.domain.playlist;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.google.gson.Gson;
import com.seb44main011.petplaylist.domain.music.dto.MusicDto;
import com.seb44main011.petplaylist.domain.music.stub.TestData;
import com.seb44main011.petplaylist.domain.playlist.service.MusicListService;
import com.seb44main011.petplaylist.domain.playlist.service.PlaylistService;
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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
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
public class PlayListControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;

    @MockBean
    private PlaylistService playlistService;

    @MockBean
    private MusicListService musicListService;

    private final String API_PLAYLIST_URL = "/api/playlist";

    @Test
    @DisplayName("개인 플레이 리스트 추가(좋아요) 기능 테스트")
    public void postPersonalPlayListTest() throws Exception {
        MusicDto.PostRequest postRequestData = TestData.MockMusic.getPostRequestData();
        String content = gson.toJson(postRequestData);

        doNothing().when(playlistService).createPersonalMusicList(Mockito.anyLong(),Mockito.anyLong());
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
    @DisplayName("개인 플레이 리스트 삭제(좋아요 삭제) 기능 테스트")
    public void deletePersonalPlayListTest() throws Exception {

        MusicDto.DeleteRequest postRequestData = TestData.MockMusic.getDeleteRequestData();
        String content = gson.toJson(postRequestData);

        doNothing().when(musicListService).deleteMusicList(Mockito.anyLong(),Mockito.anyLong());


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
