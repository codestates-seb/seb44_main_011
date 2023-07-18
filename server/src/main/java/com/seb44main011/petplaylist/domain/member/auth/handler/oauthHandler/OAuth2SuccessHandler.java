package com.seb44main011.petplaylist.domain.member.auth.handler.oauthHandler;

import com.seb44main011.petplaylist.domain.member.auth.jwt.DelegateTokenService;
import com.seb44main011.petplaylist.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@Slf4j
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final DelegateTokenService delegateTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Member memberData = (Member) authentication.getPrincipal();
        log.info("onAuthenticationSuccess MemberName: {}", memberData.getName());
        log.info("onAuthenticationSuccess MemberEmail: {}", memberData.getEmail());

        redirect(request, response, memberData);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, Member member) throws IOException {
        String accessToken = "Bearer" + delegateTokenService.delegateAccessToken(member);
        String refreshToken = delegateTokenService.delegateRefreshToken(member);
        String redirectURL = createURI(accessToken, refreshToken).toString();

        getRedirectStrategy().sendRedirect(request, response, redirectURL);
    }

    private URI createURI(String accessToken, String refreshToken) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_token", accessToken);
        queryParams.add("refresh_token", refreshToken);

        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("ec2-3-35-216-90.ap-northeast-2.compute.amazonaws.com") // TODO: 클라이언트 주소로 변경 필요
                .path("oauth")
                .queryParams(queryParams)
                .build()
                .toUri();
    }
}
