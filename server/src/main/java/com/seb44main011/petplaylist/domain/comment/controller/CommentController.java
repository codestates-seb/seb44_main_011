package com.seb44main011.petplaylist.domain.comment.controller;

import com.seb44main011.petplaylist.domain.comment.entity.Comment;
import com.seb44main011.petplaylist.domain.comment.mapper.CommentMapper;
import com.seb44main011.petplaylist.domain.comment.dto.CommentDto;
import com.seb44main011.petplaylist.domain.comment.service.CommentService;
import com.seb44main011.petplaylist.domain.music.service.mainService.MusicService;
import com.seb44main011.petplaylist.global.common.AuthenticationName;
import com.seb44main011.petplaylist.global.common.MultiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final MusicService musicService;

    @PostMapping("/api/musics/{music-id}/comments")
    public ResponseEntity<?> postComment(@PathVariable("music-id") long musicId, @Valid @RequestBody CommentDto.Post requestBody) {

        CommentDto.Response saveComment = commentService.saveComment(requestBody);

        URI location = UriComponentsBuilder
                .newInstance()
                .path("/public/musics/" + musicId + "/comments/{comment-Id}")
                .buildAndExpand(saveComment.getCommentId())
                .toUri();


        return ResponseEntity.created(location).body(saveComment);
    }

    @GetMapping("/public/musics/{music-id}/comments")
    public ResponseEntity<?> getComments(@PathVariable("music-id") long musicId, @RequestParam("page") @Positive int page) {

        MultiResponseDto musicComments = commentService.getComments(musicId, page);

//        int totalPages = musicComments.getTotalPages();
//        long totalElements = musicComments.getTotalElements();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("X-Total-Pages", String.valueOf(totalPages));
//        headers.add("X-Total-Elements", String.valueOf(totalElements));

        return ResponseEntity.ok()
//                .headers(headers)
                .body(musicComments);
    }

    @PatchMapping("/api/musics/{music-id}/comments/{comments-id}")
    public ResponseEntity<?> patchComment(@PathVariable("music-id") long musicId, @PathVariable("comments-id") long commentId, @Valid @RequestBody CommentDto.Patch requestBody) {
//      Patch시 작성자와 수정 요청자가 동일한지 확인하고 수정해야함
        commentService.updateComment(requestBody);

        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/api/musics/{music-id}/comments/{comments-id}")
    public ResponseEntity<?> deleteComment(@PathVariable("music-id") long musicId, @PathVariable("comments-id") long commentId, @AuthenticationName String email) {

        //인증정보에서 memberId 가져오는 로직 추가
        commentService.deleteComment(commentId, email);

        return ResponseEntity.ok().build();
    }


}
