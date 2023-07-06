package com.seb44main011.petplaylist.domain.comment.service;

import com.seb44main011.petplaylist.domain.comment.entity.Comment;
import com.seb44main011.petplaylist.domain.comment.repository.CommentRepository;
import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.member.repository.MemberRepository;
import com.seb44main011.petplaylist.domain.member.service.MemberService;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.repository.MusicRepository;
import com.seb44main011.petplaylist.global.error.BusinessLogicException;
import com.seb44main011.petplaylist.global.error.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final MusicRepository musicRepository;


    public Comment saveComment(Comment comment, Long memberId, Long musicId) {
        Comment resultComment = new Comment();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        resultComment.setMember(member);

        Music music = musicRepository.findById(musicId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MUSIC_NOT_FOUND));
        resultComment.setMusic(music);

        resultComment.setCommentId(comment.getCommentId());
        resultComment.setComment(comment.getComment());

        return commentRepository.save(resultComment);
    }

    public void updateComment(Comment comment) {

        Comment myComment = commentRepository.findById(comment.getCommentId()).orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        myComment.setComment(comment.getComment());
        myComment.updateModifiedAt(LocalDateTime.now());

    }

    /**
     * 음악에서 댓글 리스트 받아와서 조회하는 로직 추가해야함
     *
     */

//    public Page<Comment> getComments(long musicId, int page, int size) {
//        Music targetMusic = musicRepository.findById(musicId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MUSIC_NOT_FOUND));
//        return commentRepository.findAll(PageRequest.of(page, size));
//    }

    public void deleteComment(Long commentId) {
        Comment targetComment = commentRepository.findById(commentId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        commentRepository.delete(targetComment);
    }
}
