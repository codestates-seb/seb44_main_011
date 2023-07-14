package com.seb44main011.petplaylist.domain.music.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

public class MusicDto {


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post{
        @NotNull(message = "공백이 올 수 없습니다.")
        private long musicId;

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
package com.seb44main011.petplaylist.domain.music.dto;

import lombok.Builder;
import lombok.Getter;

public class MusicDto {
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
