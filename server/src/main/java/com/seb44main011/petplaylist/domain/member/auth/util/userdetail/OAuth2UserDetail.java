package com.seb44main011.petplaylist.domain.member.auth.util.userdetail;

import com.seb44main011.petplaylist.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

public class OAuth2UserDetail implements OAuth2User {
    public final Map<String, Object> attributes;
    public final Member.OAuthCheck oAuthCheck;
    @Setter
    @Getter
    private Long memberId;

    @Builder
    public OAuth2UserDetail(Map<String, Object> attributes, Member.OAuthCheck oAuthCheck) {
        this.attributes = attributes;
        this.oAuthCheck = oAuthCheck;
    }

    public static OAuth2UserDetail of(Map<String, Object> attributes, Member.OAuthCheck oAuthCheck){
        if (oAuthCheck.equals(Member.OAuthCheck.NAVER)){
            return naverUser(attributes);
        } else if (oAuthCheck.equals(Member.OAuthCheck.KAKAO)) {
            return kakaoUser(attributes);
        }
        return googleUser(attributes);
    }

    private static OAuth2UserDetail googleUser(Map<String, Object> attributes) {
        return OAuth2UserDetail.builder()
                .oAuthCheck(Member.OAuthCheck.GOOGLE)
                .attributes(attributes)
                .build();
    }

    private static OAuth2UserDetail kakaoUser(Map<String, Object> attributes) {
        Map<String,Object> accountMap = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>)accountMap.get("profile");
        return OAuth2UserDetail.builder()
                .oAuthCheck(Member.OAuthCheck.KAKAO)
                .attributes(
                        Map.of("email",accountMap.get("email"), "name",profile.get("nickname"))
                )
                .build();
    }

    private static OAuth2UserDetail naverUser(Map<String, Object> attributes) {
        Map<String,Object> accountMap = (Map<String, Object>) attributes.get("response");
        return OAuth2UserDetail.builder()
                .oAuthCheck(Member.OAuthCheck.NAVER)
                .attributes(accountMap)
                .build();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_USER", "USER");
    }
    public  Member.OAuthCheck  getoAuthCheck() {
        return this.oAuthCheck;
    }
    public String getEmail() {
        return (String) attributes.get("email");
    }
    public String getName() {
        return (String) attributes.get("name");
    }
}
