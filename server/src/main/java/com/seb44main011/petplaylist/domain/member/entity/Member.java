package com.seb44main011.petplaylist.domain.member.entity;

import com.seb44main011.petplaylist.domain.playlist.entity.entityTable.PlayList;
import com.seb44main011.petplaylist.global.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "MEMBER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
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

    @Column(nullable = false)
    private String profile;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Status status = Status.MEMBER_ACTIVE;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name="PERSONALPLAYLIST_ID")
//    private PersonalPlayList personalPlayList;
    @OneToMany(mappedBy = "member",cascade =CascadeType.ALL)
    private List<PlayList> playLists;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private OAuthCheck oAuthCheck = OAuthCheck.NO_OAUTH;

    @Builder
    public Member(long memberId, String email, String password, String name, String profile, Status status, List<PlayList> playLists, OAuthCheck oAuthCheck) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.profile = "기본 프로필 이미지";
        this.status = status;
        this.playLists = new ArrayList<>();
        this.oAuthCheck = oAuthCheck;
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

    public void updateProfile(String profile) {
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
