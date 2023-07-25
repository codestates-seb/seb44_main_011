package com.seb44main011.petplaylist.domain.member.auth.jwt;

import com.seb44main011.petplaylist.domain.member.auth.util.userdetail.OAuth2UserDetail;
import com.seb44main011.petplaylist.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DelegateTokenService {
    private final JwtTokenizer tokenizer;
    public String delegateAccessToken(Member member) {
        Map<String, Object> claims = getClaims(member.getEmail());
        Date expiration = tokenizer.getTokenExpiration(tokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKeySting = tokenizer.secretKeyEncodeBase64(tokenizer.getSecretKeyString());
        String subject = member.getEmail();

        return tokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKeySting);
    }

    public String delegateAccessToken(OAuth2UserDetail auth2UserDetail) {
        Map<String, Object> claims = getClaims(auth2UserDetail.getEmail());
        Date expiration = tokenizer.getTokenExpiration(tokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKeySting = tokenizer.secretKeyEncodeBase64(tokenizer.getSecretKeyString());
        String subject = auth2UserDetail.getEmail();

        return tokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKeySting);
    }

    public String delegateAccessToken(String subject) {
        Map<String, Object> claims = getClaims(subject);
        Date expiration = tokenizer.getTokenExpiration(tokenizer.getAccessTokenExpirationMinutes());
        String base64EncodedSecretKeyString = tokenizer.secretKeyEncodeBase64(tokenizer.getSecretKeyString());

        return tokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKeyString);
    }

    public String delegateRefreshToken(OAuth2UserDetail auth2UserDetail) {
        Date expiration = tokenizer.getTokenExpiration(tokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKeyString = tokenizer.secretKeyEncodeBase64(tokenizer.getSecretKeyString());
        String subject = auth2UserDetail.getEmail();

        return tokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKeyString);
    }
    public String delegateRefreshToken(Member member) {
        Date expiration = tokenizer.getTokenExpiration(tokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKeyString = tokenizer.secretKeyEncodeBase64(tokenizer.getSecretKeyString());
        String subject = member.getEmail();

        return tokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKeyString);
    }

    private static Map<String, Object> getClaims(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        return claims;
    }

}
