package com.seb44main011.petplaylist.comment.service;

import com.seb44main011.petplaylist.comment.entity.Comment;
import com.seb44main011.petplaylist.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment saveComment(Comment comment) {

        return commentRepository.save(comment);
    }

    public Comment updateComment(Comment comment) {
        Optional<Comment> myComment = commentRepository.findById(comment.getCommentId());
        if (myComment.isPresent()) {
            Comment commentObj = myComment.get();
            commentObj.setComment(comment.getComment());
            return commentObj;
        }
        return null;
    }

    public void deleteComment(Long co)
}
