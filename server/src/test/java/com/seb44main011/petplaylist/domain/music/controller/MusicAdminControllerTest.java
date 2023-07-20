//package com.seb44main011.petplaylist.domain.music.controller;
//
//import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
//import com.seb44main011.petplaylist.domain.music.mapper.MusicMapper;
//import com.seb44main011.petplaylist.domain.music.service.mainService.MusicService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import static org.mockito.Mockito.doNothing;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
//import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
//import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureRestDocs
//@AutoConfigureMockMvc
//public class MusicAdminControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private MusicMapper musicMapper;
//
//    @MockBean
//    private MusicService musicService;
//
//    private final String ADMIN_URL="/admin/music";
//    @Test
//    @DisplayName("모든 음악 조회 기능 테스트")
//    void deleteMusicFileTest() throws Exception {
//        //
//        //when
//        doNothing().when(musicService).deleteMusicFile(Mockito.anyLong());
//
//
//        //then
//        ResultActions resultActions1 =
//                mockMvc.perform(delete(ADMIN_URL.concat("/id/{music-Id}"),1L))
//                        .andExpect(status().isNoContent())
//                        .andDo(
//                                MockMvcRestDocumentationWrapper
//                                        .document("음원 파일 비활성화 기능(admin)"
//                                                ,preprocessRequest(prettyPrint())
//                                                ,preprocessResponse(prettyPrint())
//                                                ,pathParameters(
//                                                        parameterWithName("music-Id").description("음악 식별 Id")
//                                                )
//
//
//                                        )
//                        );
//
//
//    }
//}
