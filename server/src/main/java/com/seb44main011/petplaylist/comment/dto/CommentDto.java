package com.seb44main011.petplaylist.comment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class CommentDto {

    @Getter @Setter
    public static class Post {
        private String comment;
    }

    @Getter @Setter
    public static class Patch {
        private Long commentId;
        private String comment;
    }

    @Getter
    @Setter
    public static class Response {
        private Long commentId;
        private Long musicId;
        private String name;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}
