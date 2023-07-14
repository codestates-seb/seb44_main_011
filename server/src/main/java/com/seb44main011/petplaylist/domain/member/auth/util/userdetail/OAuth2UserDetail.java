package com.seb44main011.petplaylist.domain.member.auth.util.userdetail;

import com.seb44main011.petplaylist.domain.member.entity.Member;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class OAuth2UserDetail extends UserDetail implements OAuth2User {
    public final Map<String, Object> attributes;

    public OAuth2UserDetail(Member member, Map<String, Object> attributes) {
        super(member);
        this.updateMemberId(getMemberId());
        this.updateEmail(getEmail());
        this.updatePassword(getPassword());
        this.updateName(getName());
        this.updateProfile(getProfile());
        this.updatePlayLists(getPlayLists());
        this.updateOAuth(OAuthCheck.GOOGLE);
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
