package com.seb44main011.petplaylist.domain.member.auth.util.service;

import com.seb44main011.petplaylist.domain.member.auth.util.error.AuthenticationExceptionCode;
import com.seb44main011.petplaylist.domain.member.auth.util.error.OAuthErrorException;
import com.seb44main011.petplaylist.domain.member.auth.util.userdetail.OAuth2UserDetail;
import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.member.repository.MemberRepository;
import com.seb44main011.petplaylist.global.error.BusinessLogicException;
import com.seb44main011.petplaylist.global.error.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.security.Provider;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2MemberService extends DefaultOAuth2UserService {
    private final MemberRepository repository;
    private final PasswordEncoder encoder;

    @SneakyThrows
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = validateOAuth2User(userRequest);
        log.info("멤버 데이터 체크: {}", oAuth2User);

        return oAuth2User;
    }

    private OAuth2User validateOAuth2User(OAuth2UserRequest userRequest) throws IllegalAccessException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Member.OAuthCheck oAuthCheck = getOAuthClient(userRequest.getClientRegistration().getClientName());
        Map<String,Object> attributes = isCheckOAuth(oAuthCheck,oAuth2User.getAttributes());

        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");


        Optional<Member> optionalMember = repository.findByEmail(email);
        if (optionalMember.isEmpty()){
            return saveOAuthUser(email, name, oAuthCheck);
        }
        OAuth2User findOAuth2User = optionalMember.map(m->new OAuth2UserDetail(m,attributes,m.getOAuthCheck())).orElseThrow();
        Member member = (Member) findOAuth2User;

        if (member.getOAuthCheck().equals(Member.OAuthCheck.NO_OAUTH)) {
                throw new OAuthErrorException(AuthenticationExceptionCode.MEMBER_CONFLICT);
        }
        return findOAuth2User;

    }

    private Map<String, Object> isCheckOAuth(Member.OAuthCheck oAuthCheck,Map<String, Object> attributes) throws IllegalAccessException {
        Map<String,Object> newAttributes = new HashMap<>();
        log.info("getAttributes : {}",attributes);
        if (oAuthCheck.equals(Member.OAuthCheck.KAKAO)){
            Object kakao_account = attributes.get("kakao_account");
            Map<String,Object> stringObjectMap = (Map<String, Object>) kakao_account;
            String email = (String) stringObjectMap.get("email");
            stringObjectMap= (Map<String,Object>)stringObjectMap.get("profile");
            String name = (String) stringObjectMap.get("nickname");
            newAttributes.put("email",email);
            newAttributes.put("name",name);
            validateAttributes(newAttributes);
            return newAttributes;
        }
        else if(oAuthCheck.equals(Member.OAuthCheck.NAVER)){
            Object naver_account = attributes.get("response");
            Map<String,Object> stringObjectMap = (Map<String, Object>) naver_account;
            validateAttributes(stringObjectMap);
            return stringObjectMap;
        }
        validateAttributes(attributes);
        return attributes;


    }

    private static Member.OAuthCheck getOAuthClient(String clientName) throws IllegalAccessException {

        switch (clientName.toUpperCase()) {
            case "GOOGLE":
                return Member.OAuthCheck.GOOGLE;

            case "KAKAO":
                return Member.OAuthCheck.KAKAO;

            case "NAVER":
                return Member.OAuthCheck.NAVER;

            case "FACEBOOK":
                return Member.OAuthCheck.FACEBOOK;

            default:
                throw new IllegalAccessException("Unsupported email domain: " + clientName.toUpperCase());
        }
    }

    private OAuth2UserDetail saveOAuthUser(String email, String name, Member.OAuthCheck oAuthCheck) {
        String password = encoder.encode("oauthpassword123");
        Map<String, Object> attribute = new HashMap<>();
        attribute.put("email", email);
        Member createMember = Member.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
        createMember.updateOAuth(oAuthCheck);
        Member saveMember = repository.save(createMember);

        return new OAuth2UserDetail(saveMember, attribute, oAuthCheck);
    }


    private void validateAttributes(Map<String, Object> attributes) throws IllegalAccessException{
        if (!attributes.containsKey("email")) {
            throw new IllegalAccessException("응답 멤버 정보에 이메일이 없습니다.");
        }
    }
}
