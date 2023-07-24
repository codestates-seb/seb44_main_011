package com.seb44main011.petplaylist.domain.member.entity;

import com.seb44main011.petplaylist.domain.playlist.entity.entityTable.PlayList;
import com.seb44main011.petplaylist.global.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity(name = "MEMBER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Profile profile;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.MEMBER_ACTIVE;

    @OneToMany(mappedBy = "member",cascade =CascadeType.ALL)
    private List<PlayList> playLists = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private OAuthCheck oAuthCheck = OAuthCheck.NO_OAUTH;

    @Builder
    public Member(long memberId, String email, String password, String name, String profile, Status status, List<PlayList> playLists, OAuthCheck oAuthCheck) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.profile = Profile.DOG1;
        this.status = Status.MEMBER_ACTIVE;
        this.playLists = new ArrayList<>();
        this.oAuthCheck = OAuthCheck.NO_OAUTH;
    }

    public enum Profile {
        DOG1("https://main-project-file.s3.ap-northeast-2.amazonaws.com/profile/dogs/dog1.jpg"),
        DOG2("https://main-project-file.s3.ap-northeast-2.amazonaws.com/profile/dogs/dog2.jpg"),
        DOG3("https://main-project-file.s3.ap-northeast-2.amazonaws.com/profile/dogs/dog3.jpg"),
        DOG4("https://main-project-file.s3.ap-northeast-2.amazonaws.com/profile/dogs/dog4.jpg"),
        CAT1("https://main-project-file.s3.ap-northeast-2.amazonaws.com/profile/cats/cat1.png"),
        CAT2("https://main-project-file.s3.ap-northeast-2.amazonaws.com/profile/cats/cat2.png"),
        CAT3("https://main-project-file.s3.ap-northeast-2.amazonaws.com/profile/cats/cat3.jpg"),
        CAT4("https://main-project-file.s3.ap-northeast-2.amazonaws.com/profile/cats/cat4.jpg");

        @Getter
        private final String profileUrl;

        Profile(String profileUrl) {
            this.profileUrl = profileUrl;
        }
    }


    public enum Status {
        MEMBER_ACTIVE("활성 상태"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private final String status;

        Status(String status) {
            this.status = status;
        }
    }

    public enum OAuthCheck {
        GOOGLE("GOOGLE"),
        NAVER("NAVER"),
        KAKAO("KAKAO"),
        FACEBOOK("FACEBOOK"),
        NO_OAUTH("No_OAuth_Member");

        @Getter
        private final String status;

        OAuthCheck(String status) {
            this.status = status;
        }
    }

    public void updateStatus(Status status) {
        this.status = status;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateProfile(Profile profile) {
        this.profile = profile;
    }

    public void updateMemberId(long memberId) {
        this.memberId = memberId;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updatePlayLists(List<PlayList> playLists) {
        this.playLists = playLists;
    }

    public void updateOAuth(OAuthCheck oAuthCheck) {
        this.oAuthCheck = oAuthCheck;
    }
}
