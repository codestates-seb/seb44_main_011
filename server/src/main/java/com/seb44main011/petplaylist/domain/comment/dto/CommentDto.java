package com.seb44main011.petplaylist.domain.comment.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter @Setter
public class CommentDto {

    @Getter @Setter
    public static class Post {
        private Long memberId;
        private Long musicId;
        @Size(min = 2 , max = 30)
        private String comment;
    }

    @Getter @Setter
    public static class Patch {
        private Long commentId;
        @Size(min = 2 , max = 30)
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
