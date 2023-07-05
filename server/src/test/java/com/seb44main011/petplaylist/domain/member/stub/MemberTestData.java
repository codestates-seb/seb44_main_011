package com.seb44main011.petplaylist.domain.member.stub;

import com.seb44main011.petplaylist.domain.member.dto.MemberDto;
import com.seb44main011.petplaylist.domain.member.entity.Member;

public class MemberTestData {
    public static class MockMember {
        private static final String email = "hgd@gmail.com";
        private static final String password = "a12341234";
        private static final String name = "내가바로홍길동";
        private static final String profile = "프로필 기본 이미지";

        public static Member getMemberData() {
            return Member.builder()
                    .memberId(1L)
                    .email(email)
                    .password(password)
                    .name(name)
                    .profile(profile)
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
}
