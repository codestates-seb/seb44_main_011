package com.seb44main011.petplaylist.domain.comment.controller;

import com.seb44main011.petplaylist.domain.comment.entity.Comment;
import com.seb44main011.petplaylist.domain.comment.mapper.CommentMapper;
import com.seb44main011.petplaylist.domain.comment.dto.CommentDto;
import com.seb44main011.petplaylist.domain.comment.service.CommentService;
import com.seb44main011.petplaylist.global.common.MultiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;


    @PostMapping("/api/musics/{music-id}/comments")
    public ResponseEntity<?> postComment(@PathVariable("music-id") long musicId,@Valid @RequestBody CommentDto.Post requestBody) {
        Long memberId = requestBody.getMemberId();

        Comment comment = commentMapper.commentPostToComment(requestBody);
        Comment saveComment = commentService.saveComment(comment,memberId, musicId);

        URI location = UriComponentsBuilder
                .newInstance()
                .path("/public/musics/" + musicId + "/comments/{comment-Id}")
                .buildAndExpand(comment.getCommentId())
                .toUri();

        return ResponseEntity.created(location).body(commentMapper.commentToCommentResponseDto(saveComment));
    }

    @GetMapping("/public/musics/{music-id}/comments")
    public ResponseEntity<?> getComments(@PathVariable("music-id") long musicId, @RequestParam("page") int page) {
        Page<Comment> musicComments = commentService.getComments(musicId, page);

        int totalPages = musicComments.getTotalPages();
        long totalElements = musicComments.getTotalElements();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Pages", String.valueOf(totalPages));
        headers.add("X-Total-Elements", String.valueOf(totalElements));

        List<Comment> content = musicComments.getContent();
        List<CommentDto.Response> responses = commentMapper.commentDtoResponseToListCommentDtoResponse(content);

        MultiResponseDto multiResponseDto = new MultiResponseDto(responses, musicComments);
        return ResponseEntity.ok()
                .headers(headers)
                .body(multiResponseDto);
    }

    @PatchMapping("/api/music/{music-id}/comments/{comments-id}")
    public ResponseEntity<?> patchComment(@PathVariable("music-id") long musicId, @PathVariable("comments-id") long commentId, @Valid @RequestBody CommentDto.Patch requestBody) {
        Comment comment = commentMapper.commentPatchToComment(requestBody);
        commentService.updateComment(comment);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/music/{music-id}/comments/{comments-id}")
    public ResponseEntity<?> deleteComment(@PathVariable("music-id") long musicId, @PathVariable("comments-id") long commentId) {
        commentService.deleteComment(commentId);

        return ResponseEntity.ok().build();
    }


}
