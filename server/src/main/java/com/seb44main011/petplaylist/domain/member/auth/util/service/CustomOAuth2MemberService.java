package com.seb44main011.petplaylist.domain.member.auth.util.service;

import com.seb44main011.petplaylist.domain.member.auth.util.error.AuthenticationExceptionCode;
import com.seb44main011.petplaylist.domain.member.auth.util.error.OAuthErrorException;
import com.seb44main011.petplaylist.domain.member.auth.util.userdetail.OAuth2UserDetail;
import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

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
        OAuth2User oAuth2User = validateOAuth2User(userRequest);
        log.info("멤버 데이터 체크: {}", oAuth2User);

        return oAuth2User;
    }

    private OAuth2User validateOAuth2User(OAuth2UserRequest userRequest) throws IllegalAccessException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Member.OAuthCheck oAuthCheck = getOAuthClient(userRequest.getClientRegistration().getClientName());
        OAuth2UserDetail oAuth2UserDetail = OAuth2UserDetail.of(oAuth2User.getAttributes(),oAuthCheck);
        validateAttributes(oAuth2UserDetail.getAttributes());
        isOAuthChecking(oAuth2UserDetail);
        return oAuth2UserDetail;

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

    private void validateAttributes(Map<String, Object> attributes) throws IllegalAccessException{
        if (!attributes.containsKey("email")) {
            throw new IllegalAccessException("응답 멤버 정보에 이메일이 없습니다.");
        }
    }

    private void isOAuthChecking(OAuth2UserDetail oAuth2UserDetail) {
        Optional<Member> oAuthMember = repository.findByEmail(oAuth2UserDetail.getEmail());
        Member member = oAuthMember.orElseGet(()->saveOAuthUser(oAuth2UserDetail));

        if (!member.getOAuthCheck().equals(oAuth2UserDetail.getoAuthCheck())) {
            throw new OAuthErrorException(AuthenticationExceptionCode.MEMBER_CONFLICT);
        }
        oAuth2UserDetail.setMemberId(member.getMemberId());
    }
    private Member saveOAuthUser(OAuth2UserDetail oAuth2UserDetail) {
        String password = encoder.encode("oauthpassword123");
        Member createMember = Member.builder()
                .email(oAuth2UserDetail.getEmail())
                .name(oAuth2UserDetail.getName())
                .password(password)
                .build();
        createMember.updateOAuth(oAuth2UserDetail.getoAuthCheck());
        return repository.save(createMember);

    }

}
