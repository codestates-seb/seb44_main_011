package com.seb44main011.petplaylist.comment.controller;

import com.seb44main011.petplaylist.comment.entity.Comment;
import com.seb44main011.petplaylist.comment.mapper.CommentMapper;
import com.seb44main011.petplaylist.comment.dto.CommentDto;
import com.seb44main011.petplaylist.comment.repository.CommentRepository;
import com.seb44main011.petplaylist.comment.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping
public class CommentController {
    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentController(CommentService commentService, CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    @PostMapping("/api/musics/{music-id}/comments")
    public ResponseEntity<?> postComment(@PathVariable("music-id") int musicId, @RequestBody CommentDto.Post requestBody) {

        Comment comment = commentMapper.commentPostToComment(requestBody);
        Comment saveComment = commentService.saveComment(comment);

        URI location = UriComponentsBuilder
                .newInstance()
                .path()
                .buildAndExpand()
                .toUri();

        return ResponseEntity.created(location).build();

    }
}
