package com.seb44main011.petplaylist.domain.comment.dto;

import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter @Setter
public class CommentDto {

    @Getter @Setter
    @AllArgsConstructor
    public static class Post {
        private Long memberId;
        private Long musicId;
        @Size(min = 2 , max = 30)
        private String comment;
    }

    @Getter @Setter
    @AllArgsConstructor
    @Builder
    public static class Patch {
        private Long commentId;
        @Size(min = 2 , max = 30)
        private String comment;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Long commentId;
        private Long memberId;
        private Long musicId;
        private String name;
        private String comment;
        private String profile;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}
