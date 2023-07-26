package com.seb44main011.petplaylist.domain.member.auth.util.userdetail;

import com.seb44main011.petplaylist.domain.member.entity.Member;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class OAuth2UserDetail extends UserDetail implements OAuth2User {
    public final Map<String, Object> attributes;

    public OAuth2UserDetail(Member member, Map<String, Object> attributes, Member.OAuthCheck oAuthCheck) {
        super(member);
        this.updateMemberId(member.getMemberId());
        this.updateEmail(member.getEmail());
        this.updatePassword(member.getPassword());
        this.updateName(member.getName());
        this.updateProfile(member.getProfile());
        this.updatePlayLists(member.getPlayLists());
        this.updateOAuth(oAuthCheck);
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
