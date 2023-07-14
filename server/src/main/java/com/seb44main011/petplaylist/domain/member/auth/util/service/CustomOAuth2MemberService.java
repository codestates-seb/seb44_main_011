package com.seb44main011.petplaylist.domain.member.auth.util.service;

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

    private OAuth2User validateOAuth2User(Map<String, Object> attributes) {
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        Optional<Member> optionalMember = repository.findByEmail(email);

        return optionalMember.map(member -> new OAuth2UserDetail(member, attributes))
                .orElseGet(() -> saveOAuthUser(email, name));
    }

    private OAuth2UserDetail saveOAuthUser(String email, String name) {
        String password = encoder.encode("oauthpassword1234");
        Map<String, Object> attribute = new HashMap<>();
        attribute.put("email", email);
        Member createMember = Member.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
        createMember.updateOAuth(Member.OAuthCheck.GOOGLE);
        Member saveMember = repository.save(createMember);

        return new OAuth2UserDetail(saveMember, attribute);
    }

    private void validateAttributes(Map<String, Object> attributes) throws IllegalAccessException {
        if (!attributes.containsKey("email")) {
            throw new IllegalAccessException("응답 멤버 정보에 이메일이 없습니다.");
        }
    }
}
