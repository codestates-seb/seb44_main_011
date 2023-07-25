package com.seb44main011.petplaylist.domain.member.auth.handler.oauthHandler;

import com.seb44main011.petplaylist.domain.member.auth.jwt.DelegateTokenService;
import com.seb44main011.petplaylist.domain.member.auth.util.userdetail.OAuth2UserDetail;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
        OAuth2UserDetail auth2UserDetail = (OAuth2UserDetail) authentication.getPrincipal();
        log.info("onAuthenticationSuccess MemberName: {}", auth2UserDetail.getName());
        log.info("onAuthenticationSuccess MemberEmail: {}", auth2UserDetail.getEmail());

        redirect(request, response, auth2UserDetail);
    }

    @SneakyThrows
    private void redirect(HttpServletRequest request, HttpServletResponse response, OAuth2UserDetail auth2UserDetail) throws IOException {
        String accessToken = "Bearer " + delegateTokenService.delegateAccessToken(auth2UserDetail);
        String refreshToken = delegateTokenService.delegateRefreshToken(auth2UserDetail);
        Long memberId = auth2UserDetail.getMemberId();
        String redirectURL = createURI(accessToken, refreshToken, memberId).toString();

        getRedirectStrategy().sendRedirect(request, response, redirectURL);
    }

    private URI createURI(String accessToken, String refreshToken, Long memberId) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_token", accessToken);
        queryParams.add("refresh_token", refreshToken);
        queryParams.add("memberId", String.valueOf(memberId));

        return UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("on.petpil.site")
                .path("/oauth")
                .queryParams(queryParams)
                .build()
                .toUri();
    }
    ///oAuht 5173

    //TODO: 서버 용

//    UriComponentsBuilder.newInstance()
//            .scheme("http")
////                .host("ec2-3-35-216-90.ap-northeast-2.compute.amazonaws.com")
//                .host("localhost:3000")
//                .path("/oauth")
//                .queryParams(queryParams)
//                .build()
//                .toUri();
}
