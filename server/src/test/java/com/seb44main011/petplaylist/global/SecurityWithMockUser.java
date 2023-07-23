package com.seb44main011.petplaylist.global;

import com.seb44main011.petplaylist.domain.member.auth.jwt.JwtTokenizer;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SecurityWithMockUser {
    private static String ADMIN_ROLE = "ADMIN";
    private static String USER_ROLE = "USER";

    public static String getValidAccessToken(String secretKey,String role) {
        JwtTokenizer jwtTokenizer = new JwtTokenizer();
        Map<String, Object> claims = new HashMap<>();
        if (role.equals(ADMIN_ROLE)) {
            claims.put("email", "ADMIN@ADMIN.com");
        }else {
            claims.put("email", "test@test.com");
        }
        String subject = "test access token";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1);
        Date expiration = calendar.getTime();

        String base64EncodedSecretKey = jwtTokenizer.secretKeyEncodeBase64(secretKey);

        return jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);
    }
}
