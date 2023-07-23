package com.seb44main011.petplaylist.domain.member.auth.filter;

import com.seb44main011.petplaylist.domain.member.auth.jwt.DelegateTokenService;
import com.seb44main011.petplaylist.domain.member.auth.jwt.JwtTokenizer;
import com.seb44main011.petplaylist.global.error.BusinessLogicException;
import com.seb44main011.petplaylist.global.error.ExceptionCode;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;
    private final DelegateTokenService delegateTokenService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String checkJwtInHeader = request.getHeader("Authorization");
        return checkJwtInHeader == null || !checkJwtInHeader.startsWith("Bearer");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String encodeKey = jwtTokenizer.secretKeyEncodeBase64(jwtTokenizer.getSecretKeyString());
        String getToken = request.getHeader("Authorization").replace("Bearer ", "");
        try {

            Map<String, Object> claims = jwtTokenizer.getClaims(getToken, encodeKey).getBody();
            List<GrantedAuthority> authorities = getAuthorities(claims);
            createUsernamePasswordAuthenticationToken(claims, authorities);
        }
        catch (ExpiredJwtException ne) {
            reDelegateAccessToken(request, response, encodeKey);
        }
        catch (ClassCastException ce) {
            request.setAttribute("exception", ExceptionCode.ACCESS_DENIED);

        }
        catch (BusinessLogicException re) {
            request.setAttribute("exception", ExceptionCode.UNAUTHORIZED);
        }
        catch (Exception e) {
            request.setAttribute("exception", ExceptionCode.UNAUTHORIZED);
        }

        filterChain.doFilter(request, response);
    }

    private void reDelegateAccessToken(HttpServletRequest request, HttpServletResponse response, String encodeKey) {
        try {
            String getTokenRe = request.getHeader("Refresh");
            String subject = jwtTokenizer.getSubject(getTokenRe, encodeKey);
            createUsernamePasswordAuthenticationToken(subject);
            String accessToken =  delegateTokenService.delegateAccessToken(subject);
            response.setHeader("Authorization", "Bearer " + accessToken);
        }
        catch (ExpiredJwtException ec) {
            request.setAttribute("exception", ExceptionCode.EXPIRED_REFRESH_TOKEN);
        }
        catch (IllegalArgumentException ie) {
            request.setAttribute("exception", ExceptionCode.TOKEN_NOT_FOUND);
        }
    }

    private static void createUsernamePasswordAuthenticationToken(Map<String, Object> claims, List<GrantedAuthority> authorities) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(claims.get("email"), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void createUsernamePasswordAuthenticationToken(String subject) {
        List<GrantedAuthority> authorities =  jwtTokenizer.getADMIN_SUBJECT().equals(subject)
                ? AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ADMIN", "ROLE_USER", "USER")
                : AuthorityUtils.createAuthorityList("ROLE_USER", "USER");
        Authentication authentication = new UsernamePasswordAuthenticationToken(subject, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private List<GrantedAuthority> getAuthorities(Map<String, Object> claims) {
        return jwtTokenizer.getADMIN_SUBJECT().equals(claims.get("email"))
                ? AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ADMIN", "ROLE_USER", "USER")
                : AuthorityUtils.createAuthorityList("ROLE_USER", "USER");
//        ADMIN@ADMIN.com
    }
}
