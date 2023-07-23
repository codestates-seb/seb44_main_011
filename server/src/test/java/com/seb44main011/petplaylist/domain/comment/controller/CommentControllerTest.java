package com.seb44main011.petplaylist.domain.comment.controller;

import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.google.gson.Gson;
import com.seb44main011.petplaylist.domain.comment.dto.CommentDto;
import com.seb44main011.petplaylist.domain.comment.entity.Comment;
import com.seb44main011.petplaylist.domain.comment.repository.CommentRepository;
import com.seb44main011.petplaylist.domain.comment.service.CommentService;
import com.seb44main011.petplaylist.domain.comment.stub.CommentTestData;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.repository.MusicRepository;
import com.seb44main011.petplaylist.domain.music.stub.TestData;
import com.seb44main011.petplaylist.global.common.MultiResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private CommentService commentService;

    @Autowired
    MusicRepository musicRepository;

    @Autowired
    CommentRepository commentRepository;
//
////    @Autowired
////    MemberRepository memberRepository;
////
//    @Autowired
//    MusicService musicService;
//
//    @Autowired
//    MemberService memberService;
//
//    @AfterEach
//    void repositoryClear() {
//        musicRepository.deleteAll();
//    }


    @Test
    @DisplayName("댓글 작성 테스트")
    @WithMockUser

    void postCommentTest() throws Exception {

        Comment commentData = CommentTestData.MockComment.getCommentData();

        //given
        CommentDto.Post post = new CommentDto.Post(1L, 1L, "댓글입니다.");
        CommentDto.Response responseComment = new CommentDto.Response(1L, 1L, 1L, "네임", "내용", "", LocalDateTime.now(), LocalDateTime.now());
        Music music = TestData.MockMusic.getMusicData();
        musicRepository.save(music);

        given(commentService.saveComment(Mockito.any(CommentDto.Post.class)))
                .willReturn(responseComment);

        String content = gson.toJson(post);

        //when
        ResultActions actions =
                mockMvc.perform(
                        post("/api/musics/{music-id}/comments", commentData.getMusic().getMusicId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );
        actions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is(startsWith("/public/musics/"))))
                .andDo(
                        MockMvcRestDocumentation.document("인증된 사용자의 댓글 작성",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                                ResourceDocumentation.resource(
                                        ResourceSnippetParameters.builder()
                                                .description("댓글 작성")
                                                .pathParameters(
                                                        ResourceDocumentation.parameterWithName("music-id").description("음악 식별자")
                                                )
                                                .requestFields(
                                                        PayloadDocumentation.fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("사용자 정보(번호)"),
                                                        PayloadDocumentation.fieldWithPath("musicId").type(JsonFieldType.NUMBER).description("음악 정보(번호)"),
                                                        PayloadDocumentation.fieldWithPath("comment").type(JsonFieldType.STRING).description("댓글 내용")
                                                )
                                                .responseFields(
                                                        PayloadDocumentation.fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("댓글 번호"),
                                                        PayloadDocumentation.fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("멤버 아이디"),
                                                        PayloadDocumentation.fieldWithPath("musicId").type(JsonFieldType.NUMBER).description("음악 번호"),
                                                        PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description("사용자 닉네임"),
                                                        PayloadDocumentation.fieldWithPath("comment").type(JsonFieldType.STRING).description("댓글 내용"),
                                                        PayloadDocumentation.fieldWithPath("profile").type(JsonFieldType.STRING).description("작성자 프로필 이미지"),
                                                        PayloadDocumentation.fieldWithPath("createdAt").type(JsonFieldType.STRING).description("작성 시간"),
                                                        PayloadDocumentation.fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("수정 시간")
                                                )
                                                .build()
                                )
                        )

                );

    }

    @Test
    @DisplayName("댓글 수정 테스트")
    @WithMockUser
    void patchCommentTest() throws Exception {
        Comment commentData = CommentTestData.MockComment.getCommentData();
        String context = gson.toJson(CommentDto.Patch.builder()
                .commentId(1L)
                .comment("수정 코멘트")
                .build());


        ResultActions actions =
                mockMvc.perform(
                        patch("/api/musics/{music-id}/comments/{comments-id}", commentData.getMusic().getMusicId(), commentData.getCommentId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(context)
                );
        verify(commentService, times(1)).updateComment(any(CommentDto.Patch.class));
        actions
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("인증된 사용자의 댓글 수정",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                                ResourceDocumentation.resource(
                                        ResourceSnippetParameters.builder()
                                                .description("댓글 수정")
                                                .pathParameters(
                                                        ResourceDocumentation.parameterWithName("music-id").description("음악 식별자"),
                                                        ResourceDocumentation.parameterWithName("comments-id").description("댓글 식별자")
                                                )
                                                .requestFields(
                                                        PayloadDocumentation.fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("수정 대상 댓글 번호"),
                                                        PayloadDocumentation.fieldWithPath("comment").type(JsonFieldType.STRING).description("수정 댓글 내용")
                                                )
                                                .build()
                                )
                        )

                );
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    @WithMockUser
    void deleteCommentTest() throws Exception {
        Comment commentData = CommentTestData.MockComment.getCommentData();
        Music music = Music.builder().musicId(1L).build();



        ResultActions actions =
                mockMvc.perform(
                        delete("/api/musics/{music-id}/comments/{comments-id}", commentData.getMusic().getMusicId(), commentData.getCommentId())
                                .accept(MediaType.APPLICATION_JSON)

                );

        verify(commentService, times(1)).deleteComment(anyLong(), anyString());
        actions
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("인증된 사용자의 댓글 삭제",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                                ResourceDocumentation.resource(
                                        ResourceSnippetParameters.builder()
                                                .description("댓글 삭제")
                                                .pathParameters(
                                                        ResourceDocumentation.parameterWithName("music-id").description("음악 식별자"),
                                                        ResourceDocumentation.parameterWithName("comments-id").description("댓글 식별자")
                                                )
                                                .build()
                                )
                        )

                );
    }

    @Test
    @DisplayName("댓글리스트 조회 테스트")
    @WithMockUser
    void getCommentTest() throws Exception {
        Comment commentData = CommentTestData.MockComment.getCommentData();
        Page<Comment> commentPageData = TestData.ResponseData.PageNationData.getPageData(1, 6);
        List<CommentDto.Response> responseList = new ArrayList<>();
        responseList.add(new CommentDto.Response(1L, 1L, 1L, "네임", "내용", "", LocalDateTime.now(), LocalDateTime.now()));

        MultiResponseDto multiResponseDto = new MultiResponseDto(responseList, commentPageData);

        given(commentService.getComments(anyLong(), anyInt()))
                .willReturn(multiResponseDto);


        ResultActions actions =
                mockMvc.perform(
                        get("/public/musics/{music-id}/comments", commentData.getMusic().getMusicId())
                                .param("page", "1")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                );

        actions
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("해당 곡의 전체 댓글조회",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                                ResourceDocumentation.resource(
                                        ResourceSnippetParameters.builder()
                                                .description("댓글 조회")
                                                .pathParameters(
                                                        ResourceDocumentation.parameterWithName("music-id").description("음악 식별자")
                                                )
                                                .requestParameters(
                                                        ResourceDocumentation.parameterWithName("page").description("페이지 번호")
                                                )
                                                .responseFields(
                                                        PayloadDocumentation.fieldWithPath("data").type(JsonFieldType.ARRAY).description("댓글 리스트"),
                                                        PayloadDocumentation.fieldWithPath("data.[].commentId").type(JsonFieldType.NUMBER).description("댓글 번호"),
                                                        PayloadDocumentation.fieldWithPath("data.[].memberId").type(JsonFieldType.NUMBER).description("멤버 아이디"),
                                                        PayloadDocumentation.fieldWithPath("data.[].musicId").type(JsonFieldType.NUMBER).description("음악 번호"),
                                                        PayloadDocumentation.fieldWithPath("data.[].name").type(JsonFieldType.STRING).description("사용자 닉네임"),
                                                        PayloadDocumentation.fieldWithPath("data.[].comment").type(JsonFieldType.STRING).description("댓글 내용"),
                                                        PayloadDocumentation.fieldWithPath("data.[].profile").type(JsonFieldType.STRING).description("작성자 프로필 이미지"),
                                                        PayloadDocumentation.fieldWithPath("data.[].createdAt").type(JsonFieldType.STRING).description("작성시간"),
                                                        PayloadDocumentation.fieldWithPath("data.[].modifiedAt").type(JsonFieldType.STRING).description("수정시간"),
                                                        PayloadDocumentation.fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보"),
                                                        PayloadDocumentation.fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                                        PayloadDocumentation.fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 크기"),
                                                        PayloadDocumentation.fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("총 댓글 개수"),
                                                        PayloadDocumentation.fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 개수")

                                                )
                                                .build()
                                )
                        )

                );
    }
}