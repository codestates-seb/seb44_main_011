package com.seb44main011.petplaylist.domain.comment.mapper;

import com.seb44main011.petplaylist.domain.comment.dto.CommentDto;
import com.seb44main011.petplaylist.domain.comment.entity.Comment;
import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment commentPostToComment(CommentDto.Post commentDto);
    Comment commentPatchToComment(CommentDto.Patch commentDto);
    default CommentDto.Response commentToCommentResponseDto(Comment comment) {
        CommentDto.Response response = new CommentDto.Response();
        response.setCommentId(comment.getCommentId());
        response.setMusicId(comment.getMusic().getMusicId());
        response.setName(comment.getMember().getName());
        response.setComment(comment.getComment());
        response.setCreatedAt(comment.getCreatedAt());
        response.setModifiedAt(comment.getModifiedAt());

        return response;
    }
}