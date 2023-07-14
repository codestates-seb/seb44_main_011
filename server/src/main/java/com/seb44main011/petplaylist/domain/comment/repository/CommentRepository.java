package com.seb44main011.petplaylist.domain.comment.repository;

import com.seb44main011.petplaylist.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByMusic_MusicId(Long musicId, Pageable pageable);

//    Optional<Comment> findByCommentId(Long commentId);
    }

