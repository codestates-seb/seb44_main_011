package com.seb44main011.petplaylist.domain.music.stub;

import com.seb44main011.petplaylist.domain.music.dto.MusicDto;
import com.seb44main011.petplaylist.domain.music.entity.Music;

public class TestData {
    public static class MockMusic{
        private static final String title = "곡의 이름입니다";
        private static final String music_url="/music/test.mp3";
        private static final String image_url="/img/test.jpg";

        public static Music getMusicData(){
            return Music.builder()
                    .musicId(1L)
                    .title(title)
                    .music_url(music_url)
                    .image_url(image_url)
                    .build();
        }
        public static MusicDto.PublicResponse getPublicResponseData(){
            return MusicDto.PublicResponse.builder()
                    .musicId(1L)
                    .title(title)
                    .music_url(music_url)
                    .image_url(image_url)
                    .tags(Music.Category.DOGS.getCategory())
                    .build();

        }

        public static MusicDto.PostRequest getPostRequestData(){
            return MusicDto.PostRequest.builder()
                    .musicId(1L)
                    .build();
        }
        public static MusicDto.DeleteRequest getDeleteRequestData(){
            return MusicDto.DeleteRequest.builder()
                    .musicId(1L)
                    .build();
        }

    }
}
