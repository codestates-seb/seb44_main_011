package com.seb44main011.petplaylist.domain.member.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenizer {
    @Getter
    @Setter
    @Value("${User.mail.admin}")
    private String ADMIN_SUBJECT;

    @Getter
    @Setter
    @Value("${jwt.secretKey}")
    private String secretKeyString;

    @Getter
    @Setter
    @Value("${jwt.access-token-expiration}")
    private int accessTokenExpirationMinutes;

    @Getter
    @Setter
    @Value("${jwt.refresh-token-expiration}")
    private int refreshTokenExpirationMinutes;

    public String secretKeyEncodeBase64(String secretKeyString) {
        return Encoders.BASE64.encode(secretKeyString.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Map<String, Object> claims,
                                      String subject,
                                      Date expiration,
                                      String base64EncodedSecretKeyString) {
        Key key = getKeyBase64DecodedKey(base64EncodedSecretKeyString);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setExpiration(expiration)
                .setIssuedAt(Calendar.getInstance().getTime())
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(String subject,
                                       Date expiration,
                                       String base64EncodedSecretKeyString) {
        Key key = getKeyBase64DecodedKey(base64EncodedSecretKeyString);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    public Date getTokenExpiration(int expirationMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationMinutes);

        return calendar.getTime();
    }

    public Jws<Claims> getClaims(String jws, String base64EncodedSecretKeyString) {
        Key key = getKeyBase64DecodedKey(base64EncodedSecretKeyString);

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
    }

    public String getSubject(String jws, String base64EncodedSecretKeyString) {
        Key key = getKeyBase64DecodedKey(base64EncodedSecretKeyString);

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws)
                .getBody()
                .getSubject();
    }

    private Key getKeyBase64DecodedKey(String base64EncodedSecretKeyString) {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64EncodedSecretKeyString));
    }
}
