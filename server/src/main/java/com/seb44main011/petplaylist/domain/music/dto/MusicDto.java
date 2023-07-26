package com.seb44main011.petplaylist.domain.music.dto;

import com.seb44main011.petplaylist.domain.music.entity.Music;
import lombok.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class MusicDto {


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostRequest {
        @NotNull(message = "공백이 올 수 없습니다.")
        private long musicId;

    }
    @Getter
    @Builder
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostMusicFile implements Serializable {
        @Column(nullable = false)
        private String title;
        @Column(nullable = false)
        private Music.Category category;
        @Column(nullable = false)
        private Music.Tags tags;
//        private MultipartHttpServletRequest httpServletRequest;


    }
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeleteRequest {
        @NotNull(message = "공백이 올 수 없습니다.")
        private long musicId;

    }

    @Getter
    @Builder
    public static class PublicResponse{
        @Setter
        private Long musicId;
        private String title;
        private String music_url;
        private String image_url;
        private String playtime;
        private String tags;

    }

    @Getter
    @Builder
    public static class ApiResponse{
        private Long musicId;
        private String title;
        private String music_url;
        private String image_url;
        private String playtime;
        private String tags;
        private boolean liked;

    }
    @Getter
    @Builder
    public static class AdminResponse{
        private Long musicId;
        private String title;
        private String music_url;
        private String image_url;
        private String playtime;
        private String tags;
        private String status;

    }
}
