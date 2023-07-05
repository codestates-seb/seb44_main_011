package com.seb44main011.petplaylist.domain.music.dto;

import lombok.Builder;
import lombok.Getter;

public class MusicDto {


    @Getter
    @Builder
    public static class Post{
        private Long musicId;

    }

    @Getter
    @Builder
    public static class PublicResponse{
        private Long musicId;
        private String title;
        private String music_url;
        private String image_url;
        private String tags;

    }

    @Getter
    public static class ApiResponse{
        private Long musicId;
        private String title;
        private String music_url;
        private String image_url;
        private String tags;
        private String liked;

    }
}
