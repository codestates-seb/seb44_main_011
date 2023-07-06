package com.seb44main011.petplaylist.domain.comment.controller;

import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.google.gson.Gson;
import com.seb44main011.petplaylist.domain.comment.dto.CommentDto;
import com.seb44main011.petplaylist.domain.comment.entity.Comment;
import com.seb44main011.petplaylist.domain.comment.mapper.CommentMapper;
import com.seb44main011.petplaylist.domain.comment.service.CommentService;
import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.member.service.MemberService;
import com.seb44main011.petplaylist.domain.member.stub.MemberTestData;
import com.seb44main011.petplaylist.domain.music.dto.MusicDto;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.repository.MusicRepository;
import com.seb44main011.petplaylist.domain.music.stub.MusicTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;

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

    @MockBean
    private MemberService memberService;

    @MockBean
    private MusicRepository musicRepository;

    @MockBean
    private CommentMapper commentMapper;


    @Test
    @DisplayName("댓글 작성 테스트")
    void postCommentTest() throws Exception {
//
//        Music mockMusic = MusicTestData.MockMusic.getMusicData();
//        Member mockMember = MemberTestData.MockMember.getMemberData();
//        System.out.println("mockMusic.getMusicId() = " + mockMusic.getMusicId());

        //given
        CommentDto.Post post = new CommentDto.Post(1L, 1L, "댓글입니다.");
        Comment comment = new Comment();
        CommentDto.Response responseComment = new CommentDto.Response(1L, 1L, "네임", "내용", LocalDateTime.now(), LocalDateTime.now() );

        given(commentMapper.commentPostToComment(Mockito.any(CommentDto.Post.class)))
                .willReturn(comment);
        given(commentService.saveComment(Mockito.any(Comment.class),Mockito.anyLong(), Mockito.anyLong()))
                .willReturn(comment);
        given(commentMapper.commentToCommentResponseDto(Mockito.any(Comment.class)))
                .willReturn(responseComment);

        String content = gson.toJson(post);

        //when
        ResultActions actions =
                mockMvc.perform(
                        post("/api/musics/1/comments")
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
                                        .requestFields(
                                                PayloadDocumentation.fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("사용자 정보(번호)"),
                                                PayloadDocumentation.fieldWithPath("musicId").type(JsonFieldType.NUMBER).description("음악 정보(번호)"),
                                                PayloadDocumentation.fieldWithPath("comment").type(JsonFieldType.STRING).description("댓글 내용")
                                        )
                                        .responseFields(
                                                PayloadDocumentation.fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("댓글 번호"),
                                                PayloadDocumentation.fieldWithPath("musicId").type(JsonFieldType.NUMBER).description("음악 번호"),
                                                PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description("사용자 닉네임"),
                                                PayloadDocumentation.fieldWithPath("comment").type(JsonFieldType.STRING).description("댓글 내용"),
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
    void patchCommentTest() throws Exception {
        CommentDto.Patch patch = new CommentDto.Patch(1L, "댓글입니다.");
        Comment comment = new Comment();
        String context = gson.toJson(CommentDto.Patch.builder()
                        .commentId(1L)
                .comment("수정 코멘트")
                .build());


        given(commentMapper.commentPatchToComment(Mockito.any(CommentDto.Patch.class)))
                .willReturn(comment);


        ResultActions actions =
                mockMvc.perform(
                        patch("/api/music/1/comments/1")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(context)
                );
        verify(commentService, times(1)).updateComment(any(Comment.class));
        actions
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("인증된 사용자의 댓글 수정",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                                ResourceDocumentation.resource(
                                        ResourceSnippetParameters.builder()
                                                .description("댓글 수정")
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
    void deleteCommentTest() throws Exception {
        CommentDto.Patch patch = new CommentDto.Patch(1L, "댓글입니다.");
        Comment comment = new Comment();
        String context = gson.toJson(CommentDto.Patch.builder()
                .commentId(1L)
                .comment("수정 코멘트")
                .build());


        given(commentMapper.commentPatchToComment(Mockito.any(CommentDto.Patch.class)))
                .willReturn(comment);


        ResultActions actions =
                mockMvc.perform(
                        patch("/api/music/1/comments/1")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(context)
                );
        verify(commentService, times(1)).updateComment(any(Comment.class));
        actions
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("인증된 사용자의 댓글 수정",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                                ResourceDocumentation.resource(
                                        ResourceSnippetParameters.builder()
                                                .description("댓글 수정")
                                                .requestFields(
                                                        PayloadDocumentation.fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("수정 대상 댓글 번호"),
                                                        PayloadDocumentation.fieldWithPath("comment").type(JsonFieldType.STRING).description("수정 댓글 내용")
                                                )
                                                .build()
                                )
                        )

                );
    }

}