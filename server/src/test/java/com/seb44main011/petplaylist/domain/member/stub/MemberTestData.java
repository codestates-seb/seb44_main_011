package com.seb44main011.petplaylist.domain.member.stub;

import com.seb44main011.petplaylist.domain.member.dto.MemberDto;
import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.stub.TestData;

import java.util.ArrayList;
import java.util.List;

public class MemberTestData {
    public static class MockMember {
        private static final String email = "hgd@gmail.com";
        private static final String password = "a12341234";
        private static final String name = "내가바로홍길동";

        public static Member getMemberData() {
            return Member.builder()
                    .memberId(1L)
                    .email(email)
                    .password(password)
                    .name(name)
                    .build();
        }

        public static MemberDto.SignUpPost getSignUpPostData() {
            return MemberDto.SignUpPost.builder()
                    .email(email)
                    .password(password)
                    .name(name)
                    .build();
        }

        public static MemberDto.SignUpResponse getSignUpResponse() {
            return MemberDto.SignUpResponse.builder()
                    .memberId(1L)
                    .email(email)
                    .name(name)
                    .build();
        }
    }

    public static MemberDto.MyPageResponse getMyPageResponse() {
        return MemberDto.MyPageResponse.builder()
                .name(MockMember.name)
                .email(MockMember.email)
                .musicLists(TestData.ResponseData.Public.getPublicCategoryPlayListResponseList())
                .build();
    }

    public static class MockPersonalList {
        public static List<Music> getMusicList() {
            Music music1 = Music.builder()
                    .musicId(1L)
                    .title("Title1")
                    .music_url("url1")
                    .image_url("image1")
                    .category(Music.Category.DOGS)
                    .tags(Music.Tags.HAPPY)
                    .build();

            Music music2 = Music.builder()
                    .musicId(2L)
                    .title("Title2")
                    .music_url("url2")
                    .image_url("image2")
                    .category(Music.Category.DOGS)
                    .tags(Music.Tags.HAPPY)
                    .build();

            Music music3 = Music.builder()
                    .musicId(3L)
                    .title("Title3")
                    .music_url("url3")
                    .image_url("image3")
                    .category(Music.Category.DOGS)
                    .tags(Music.Tags.HAPPY)
                    .build();

            Music music4 = Music.builder()
                    .musicId(4L)
                    .title("Title4")
                    .music_url("url4")
                    .image_url("image4")
                    .category(Music.Category.DOGS)
                    .tags(Music.Tags.HAPPY)
                    .build();

            Music music5 = Music.builder()
                    .musicId(5L)
                    .title("Title5")
                    .music_url("url5")
                    .image_url("image5")
                    .category(Music.Category.DOGS)
                    .tags(Music.Tags.HAPPY)
                    .build();

            Music music6 = Music.builder()
                    .musicId(6L)
                    .title("Title6")
                    .music_url("url6")
                    .image_url("image6")
                    .category(Music.Category.DOGS)
                    .tags(Music.Tags.HAPPY)
                    .build();

            List<Music> musicList = new ArrayList<>();
            musicList.add(music1);
            musicList.add(music2);
            musicList.add(music3);
            musicList.add(music4);
            musicList.add(music5);
            musicList.add(music6);

            return musicList;
        }
    }
}