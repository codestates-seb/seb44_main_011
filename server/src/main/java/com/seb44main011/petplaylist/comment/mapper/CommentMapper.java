package com.seb44main011.petplaylist.comment.mapper;

import com.seb44main011.petplaylist.comment.dto.CommentDto;
import com.seb44main011.petplaylist.comment.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment commentPostToComment(CommentDto.Post requestBody);

    CommentDto.Post commentToCommentPostDto(Comment requestBody);
}
