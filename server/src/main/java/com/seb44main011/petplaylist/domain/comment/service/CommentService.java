package com.seb44main011.petplaylist.domain.comment.service;

import com.seb44main011.petplaylist.domain.comment.dto.CommentDto;
import com.seb44main011.petplaylist.domain.comment.entity.Comment;
import com.seb44main011.petplaylist.domain.comment.mapper.CommentMapper;
import com.seb44main011.petplaylist.domain.comment.repository.CommentRepository;
import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.member.service.MemberService;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.service.mainService.MusicService;
import com.seb44main011.petplaylist.global.common.MultiResponseDto;
import com.seb44main011.petplaylist.global.error.BusinessLogicException;
import com.seb44main011.petplaylist.global.error.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    // 각 도메인 Repository에 직접 접근하지 않도록 리팩토링
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final MemberService memberService;
    private final MusicService musicService;

// 리팩토링 -> comment, musicId, memberId를 파라미터로 받는거에서 CommentDto.post만 받아 처리하는걸로 변경
    public CommentDto.Response saveComment(CommentDto.Post comment) {

        Comment resultComment = new Comment();
        Member member = memberService.findMember(comment.getMemberId());
        resultComment.setMember(member);

        Music music = musicService.findMusic(comment.getMusicId());
        resultComment.setMusic(music);

        resultComment.setComment(comment.getComment());
        commentRepository.save(resultComment);

        return commentMapper.commentToCommentResponseDto(resultComment);
    }

    // 리팩토링 -> comment와 memberId 일치여부 확인
    public void updateComment(CommentDto.Patch request) {
        Comment targetComment = commentRepository.findById(request.getCommentId()).orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));


        targetComment.setComment(request.getComment());
        targetComment.updateModifiedAt(LocalDateTime.now());

        commentRepository.save(targetComment);
    }

    // 리팩토링 -> music 존재여부는 Controller에서 AOP로 우선 처리
    public MultiResponseDto getComments(long musicId, int page) {

        Pageable pageable = PageRequest.of(page-1, 6, Sort.by("createdAt").descending());

        Page<Comment> commentsPage = commentRepository.findByMusic_MusicId(musicId, pageable);
        List<Comment> commentList = commentsPage.getContent();
        List<CommentDto.Response> responses = commentMapper.commentDtoResponseToListCommentDtoResponse(commentList);

        MultiResponseDto multiResponseDto = new MultiResponseDto(responses, commentsPage);
        return multiResponseDto;
    }

    public void deleteComment(long commentId, String email) {
        findVerifiedComment(commentId);
        Member byMemberFromEmail = memberService.findByMemberFromEmail(email);
        Comment myComment = findMyComment(commentId, byMemberFromEmail.getMemberId());
        commentRepository.delete(myComment);
    }

    public Comment findMyComment(long commentId, long memberId) {
        findVerifiedComment(commentId);
        Comment myComment = commentRepository.findById(commentId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        if (myComment.getMember().getMemberId() != memberId) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }
        return myComment;
    }

    public Comment findVerifiedComment(long commentId) {
        return commentRepository.findById(commentId).
                orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
    }
}
