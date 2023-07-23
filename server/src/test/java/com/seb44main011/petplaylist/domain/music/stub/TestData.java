package com.seb44main011.petplaylist.domain.music.stub;

import com.seb44main011.petplaylist.domain.member.stub.MemberTestData;
import com.seb44main011.petplaylist.domain.music.dto.MusicDto;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.playlist.dto.PlaylistDto;
import com.seb44main011.petplaylist.domain.playlist.entity.entityTable.PlayList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TestData {
    private static final String title = "곡의 이름입니다";
    private static final String music_url="/music/test.mp3";
    private static final String image_url="/img/test.jpg";
    private static final int PAGE_SIZE =6;

    public static class MockMusic{


        public static Music getMusicData(){
            return Music.builder()
                    .musicId(1L)
                    .title(title)
                    .music_url(music_url)
                    .image_url(image_url)
                    .playtime("3:20")
                    .category(Music.Category.CATS)
                    .tags(Music.Tags.CALM)
                    .build();
        }
        public static MusicDto.PublicResponse getPublicResponseData(){
            return MusicDto.PublicResponse.builder()
                    .musicId(1L)
                    .title(title)
                    .music_url(music_url)
                    .image_url(image_url)
                    .playtime("3:20")
                    .tags(Music.Tags.CALM.getTags())
                    .build();

        }
        public static MusicDto.ApiResponse getApiResponseData(){
            return MusicDto.ApiResponse.builder()
                    .musicId(1L)
                    .title(title)
                    .music_url(music_url)
                    .image_url(image_url)
                    .playtime("3:20")
                    .tags(Music.Category.DOGS.getCategory())
                    .liked(true)
                    .build();

        }

        public static MusicDto.PostRequest getPostRequestData(){
            return MusicDto.PostRequest.builder()
                    .musicId(1L)
                    .build();
        }
        public static MusicDto.PostMusicFile getPostMusicFile(){
            return MusicDto.PostMusicFile.builder()
                    .title("MusicTitle")
                    .tags(Music.Tags.CALM)
                    .category(Music.Category.CATS)
                    .build();
        }
        public static MusicDto.DeleteRequest getDeleteRequestData(){
            return MusicDto.DeleteRequest.builder()
                    .musicId(1L)
                    .build();
        }

    }
    public static class MockMusicFile{
        public static MockMultipartFile getImgMultipartFile(){
            return new MockMultipartFile("img", "musicImgFile.png(jpg,jpeg)", "multipart/mixed","musicImgFile".getBytes(StandardCharsets.UTF_8));
        }
        public static MockMultipartFile getMp3MultipartFile(){
            return new MockMultipartFile("mp3", "musicFile.mp3", "multipart/mixed","musicFile".getBytes(StandardCharsets.UTF_8));
        }
    }

    public static class MockMusicList{
        public static PlayList getMusicListData(){
            return PlayList.builder()
                    .member(MemberTestData.MockMember.getMemberData())
                    .music(TestData.MockMusic.getMusicData())
                    .build();
        }
    }
    public static class ResponseData {
        public static class PageNationData<T>{
            public static <T> Page<T> getPageData(int page,int totalTestPageElement){
                Pageable pageable = PageRequest.of(page-1,PAGE_SIZE);
                List<T> mockList = new ArrayList<>();
                return new PageImpl<>(mockList,pageable,totalTestPageElement);
            }
        }
        public static class Api {
            public static PlaylistDto.ApiResponse getApiCategoryPlayListResponse() {
                return PlaylistDto.ApiResponse.builder()
                        .musicId(1L)
                        .title("노래제목입니다.")
                        .music_url("test.com/test/test.mp3")
                        .image_url("test.com/test/test.jpg")
                        .playtime("3:20")
                        .category(Music.Category.DOGS.getCategory())
                        .tags(Music.Tags.RELAXING.getTags())
                        .liked(true)
                        .build();
            }
            public static PlaylistDto.ApiResponse getPlayListResponse() {
                return PlaylistDto.ApiResponse.builder()
                        .musicId(1L)
                        .title("노래제목입니다.")
                        .music_url("test.com/test/test.mp3")
                        .image_url("test.com/test/test.jpg")
                        .playtime("3:20")
                        .category(Music.Category.DOGS.getCategory())
                        .tags(Music.Tags.RELAXING.getTags())
                        .liked(true)
                        .build();
            }

            public static List<PlaylistDto.ApiResponse> getApiCategorySerchPlayListResponseList() {
                return List.of(
                        getApiCategoryPlayListResponse(),
                        PlaylistDto.ApiResponse.builder()
                                .musicId(1L)
                                .title("노래제목입니다.")
                                .music_url("test.com/test2/test2.mp3")
                                .image_url("test.com/test2/test2.jpg")
                                .playtime("3:20")
                                .category(Music.Category.DOGS.getCategory())
                                .tags(Music.Tags.RELAXING.getTags())
                                .liked(false)
                                .build(),
                        PlaylistDto.ApiResponse.builder()
                                .musicId(1L)
                                .title("노래제목입니다.")
                                .music_url("test.com/test4/test4.mp3")
                                .image_url("test.com/test4/test4.jpg")
                                .playtime("3:20")
                                .category(Music.Category.DOGS.getCategory())
                                .tags(Music.Tags.RELAXING.getTags())
                                .liked(true)
                                .build()
                );
            }
            public static List<PlaylistDto.ApiResponse> getPlayListResponseList() {
                return new ArrayList<>(List.of(
                        getPlayListResponse(),
                        PlaylistDto.ApiResponse.builder()
                                .musicId(2L)
                                .title("노래제목입니다.")
                                .music_url("test.com/test2/test2.mp3")
                                .image_url("test.com/test2/test2.jpg")
                                .playtime("3:20")
                                .category(Music.Category.DOGS.getCategory())
                                .tags(Music.Tags.RELAXING.getTags())
                                .liked(true)
                                .build(),
                        PlaylistDto.ApiResponse.builder()
                                .musicId(3L)
                                .title("노래제목입니다.")
                                .playtime("3:20")
                                .music_url("test.com/test4/test4.mp3")
                                .image_url("test.com/test4/test4.jpg")
                                .category(Music.Category.DOGS.getCategory())
                                .tags(Music.Tags.HAPPY.getTags())
                                .liked(true)
                                .build(),
                        PlaylistDto.ApiResponse.builder()
                                .musicId(4L)
                                .title("노래제목입니다")
                                .music_url("test.com/test2/test4.mp3")
                                .image_url("test.com/test2/test4.jpg")
                                .playtime("3:20")
                                .category(Music.Category.CATS.getCategory())
                                .tags(Music.Tags.HAPPY.getTags())
                                .build()
                ));
            }
        }
        public static class Public{
            public static PlaylistDto.PublicResponse getPublicCategoryPlayListResponse(){
                return PlaylistDto.PublicResponse.builder()
                        .musicId(1L)
                        .title("노래제목입니다.")
                        .music_url("test.com/test/test.mp3")
                        .image_url("test.com/test/test.jpg")
                        .playtime("3:20")
                        .category(Music.Category.CATS.getCategory())
                        .tags(Music.Tags.HAPPY.getTags())
                        .build();
            }

            public static List<PlaylistDto.PublicResponse> getPublicCategoryPlayListResponseList(){
                return List.of(
                        PlaylistDto.PublicResponse.builder()
                                .musicId(7L)
                                .title("7번째 곡")
                                .music_url("test.com/test1/test1.mp3")
                                .image_url("test.com/test1/test1.jpg")
                                .playtime("3:20")
                                .category(Music.Category.CATS.getCategory())
                                .tags(Music.Tags.HAPPY.getTags())
                                .build(),
                        PlaylistDto.PublicResponse.builder()
                                .musicId(8L)
                                .title("8번째 곡")
                                .music_url("test.com/test2/test2.mp3")
                                .image_url("test.com/test2/test2.jpg")
                                .playtime("3:20")
                                .category(Music.Category.CATS.getCategory())
                                .tags(Music.Tags.HAPPY.getTags())
                                .build(),
                        PlaylistDto.PublicResponse.builder()
                                .musicId(9L)
                                .title("9번째 곡")
                                .music_url("test.com/test2/test3.mp3")
                                .image_url("test.com/test2/test3.jpg")
                                .playtime("3:20")
                                .category(Music.Category.CATS.getCategory())
                                .tags(Music.Tags.HAPPY.getTags())
                                .build(),
                        PlaylistDto.PublicResponse.builder()
                                .musicId(10L)
                                .title("10번째 곡")
                                .music_url("test.com/test2/test4.mp3")
                                .image_url("test.com/test2/test4.jpg")
                                .playtime("3:20")
                                .category(Music.Category.CATS.getCategory())
                                .tags(Music.Tags.HAPPY.getTags())
                                .build()

                );
            }
        }
        public static class Admin{
            public static MusicDto.AdminResponse getAdminResponse(){
                return MusicDto.AdminResponse.builder()
                        .musicId(1L)
                        .title("testName")
                        .music_url(music_url)
                        .image_url(image_url)
                        .playtime("3:20")
                        .tags(Music.Category.DOGS.getCategory())
                        .status(Music.Status.ACTIVE.getStatus())
                        .build();
            }
            public static List <MusicDto.AdminResponse> getAdminResponseList(){
                return List.of(
                        getAdminResponse(),
                        MusicDto.AdminResponse.builder()
                                .musicId(2L)
                                .title("testName2")
                                .music_url(music_url+"1")
                                .image_url(image_url+"1")
                                .playtime("3:20")
                                .tags(Music.Category.DOGS.getCategory())
                                .status(Music.Status.INACTIVE.getStatus())
                                .build(),
                        MusicDto.AdminResponse.builder()
                                .musicId(3L)
                                .title("testName3")
                                .music_url(music_url+"1")
                                .image_url(image_url+"1")
                                .playtime("3:20")
                                .tags(Music.Category.CATS.getCategory())
                                .status(Music.Status.INACTIVE.getStatus())
                                .build()

                        );
            }
            public static List <MusicDto.AdminResponse> getAdminActiveResponseList(){
                return List.of(
                        MusicDto.AdminResponse.builder()
                                .musicId(1L)
                                .title("testName")
                                .music_url(music_url)
                                .image_url(image_url)
                                .playtime("3:20")
                                .tags(Music.Category.DOGS.getCategory())
                                .status(Music.Status.ACTIVE.getStatus())
                                .build(),
                        MusicDto.AdminResponse.builder()
                                .musicId(2L)
                                .title("testName2")
                                .music_url(music_url+"1")
                                .image_url(image_url+"1")
                                .playtime("3:20")
                                .tags(Music.Category.DOGS.getCategory())
                                .status(Music.Status.ACTIVE.getStatus())
                                .build(),
                        MusicDto.AdminResponse.builder()
                                .musicId(3L)
                                .title("testName3")
                                .music_url(music_url+"1")
                                .image_url(image_url+"1")
                                .playtime("3:20")
                                .tags(Music.Category.CATS.getCategory())
                                .status(Music.Status.ACTIVE.getStatus())
                                .build()

                );
            }
            public static List <MusicDto.AdminResponse> getAdminInactiveResponseList(){
                return List.of(
                        getAdminResponse(),
                        MusicDto.AdminResponse.builder()
                                .musicId(2L)
                                .title("testName2")
                                .music_url(music_url+"1")
                                .image_url(image_url+"1")
                                .playtime("3:20")
                                .tags(Music.Category.DOGS.getCategory())
                                .status(Music.Status.INACTIVE.getStatus())
                                .build(),
                        MusicDto.AdminResponse.builder()
                                .musicId(3L)
                                .title("testName3")
                                .music_url(music_url+"1")
                                .image_url(image_url+"1")
                                .playtime("3:20")
                                .tags(Music.Category.CATS.getCategory())
                                .status(Music.Status.INACTIVE.getStatus())
                                .build()

                );
            }
        }

    }
}