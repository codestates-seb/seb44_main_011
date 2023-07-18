package com.seb44main011.petplaylist.domain.member.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seb44main011.petplaylist.domain.member.auth.jwt.DelegateTokenService;
import com.seb44main011.petplaylist.domain.member.dto.MemberDto;
import com.seb44main011.petplaylist.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final DelegateTokenService delegateTokenService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            MemberDto.LogInPost logInPost = objectMapper.readValue(request.getInputStream(), MemberDto.LogInPost.class);
            log.info("logInPost: {}",logInPost.getEmail());
            log.info("logInPost: {}",logInPost.getPassword());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(logInPost.getEmail(), logInPost.getPassword());
            log.info("logInPost: {}",logInPost.getEmail());
            log.info("logInPost: {}",logInPost.getPassword());

            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException("읽어올 수 없는 사용자 정보 입니다.");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Member member = (Member) authResult.getPrincipal();
        String accessToken = delegateTokenService.delegateAccessToken(member);
        String refreshToken = delegateTokenService.delegateRefreshToken(member);
        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("Refresh", refreshToken);

        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }
}
