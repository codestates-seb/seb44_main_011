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
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2MemberService extends DefaultOAuth2UserService {
    private final MemberRepository repository;
    private final PasswordEncoder encoder;

    @SneakyThrows
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        validateAttributes(oAuth2User.getAttributes());
        OAuth2User newOAuth2User = validateOAuth2User(oAuth2User.getAttributes());
        log.info("멤버 데이터 체크: {}", oAuth2User);

        return newOAuth2User;
    }

    private OAuth2User validateOAuth2User(Map<String, Object> attributes) throws IllegalAccessException {
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String domain = email.substring(email.lastIndexOf('@') + 1);
        Member.OAuthCheck oAuthCheck;
        switch (domain) {
            case "gmail.com":
                oAuthCheck = Member.OAuthCheck.GOOGLE;
                break;
            case "kakao.com":
                oAuthCheck = Member.OAuthCheck.KAKAO;
                break;
            case "naver.com":
                oAuthCheck = Member.OAuthCheck.NAVER;
                break;
            default:
                throw new IllegalAccessException("Unsupported email domain: " + domain);
        }
        Optional<Member> optionalMember = repository.findByEmail(email);
        if (optionalMember.isEmpty()){
            return saveOAuthUser(email, name, oAuthCheck);
        }
        OAuth2User oAuth2User = optionalMember.map(m->new OAuth2UserDetail(m,attributes,m.getOAuthCheck())).orElseThrow();
        Member member = (Member) oAuth2User;

        if (member.getOAuthCheck().equals(Member.OAuthCheck.NO_OAUTH)) {
                throw new OAuthErrorException(AuthenticationExceptionCode.MEMBER_CONFLICT);
        }
        return oAuth2User;

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
