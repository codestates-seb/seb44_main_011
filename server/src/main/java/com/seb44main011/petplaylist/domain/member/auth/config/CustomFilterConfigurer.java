package com.seb44main011.petplaylist.domain.member.auth.config;

import com.seb44main011.petplaylist.domain.member.auth.filter.JwtAuthenticationFilter;
import com.seb44main011.petplaylist.domain.member.auth.filter.JwtVerificationFilter;
import com.seb44main011.petplaylist.domain.member.auth.handler.loginhandler.UserAuthenticationFailureHandler;
import com.seb44main011.petplaylist.domain.member.auth.handler.loginhandler.UserAuthenticationSuccessHandler;
import com.seb44main011.petplaylist.domain.member.auth.jwt.DelegateTokenService;
import com.seb44main011.petplaylist.domain.member.auth.jwt.JwtTokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
    private final JwtTokenizer jwtTokenizer;
    private final DelegateTokenService delegateTokenService;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, delegateTokenService);
        jwtAuthenticationFilter.setFilterProcessesUrl("/public/login");
        jwtAuthenticationFilter.setAuthenticationSuccessHandler(new UserAuthenticationSuccessHandler());
        jwtAuthenticationFilter.setAuthenticationFailureHandler(new UserAuthenticationFailureHandler());
        JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, delegateTokenService);

        builder.addFilterAfter(jwtVerificationFilter, OAuth2LoginAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, JwtVerificationFilter.class);
    }
}
