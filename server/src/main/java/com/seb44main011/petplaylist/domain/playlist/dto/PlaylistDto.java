package com.seb44main011.petplaylist.domain.playlist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PlaylistDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PublicResponse {
        private Long musicId;
        private String title;
        private String music_url;

        private String image_url;
        private String playtime;

        private String category;

        private String tags;
    }
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ApiResponse {
        private Long musicId;
        private String title;
        private String music_url;

        private String image_url;
        private String playtime;

        private String category;

        private String tags;
        private boolean liked;
    }

//    @Getter
//    @Builder
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public static class PlayListResponse{
//        private Long musicId;
//        private String title;
//        private String music_url;
//
//        private String image_url;
//
//        private String category;
//
//        private String tags;
//        private boolean liked;
//    }
}
