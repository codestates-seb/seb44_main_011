package com.seb44main011.petplaylist.domain.member.auth.util.error;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

public class OAuthErrorException extends AuthenticationException {

    @Getter
    private AuthenticationExceptionCode exceptionCode;
    public OAuthErrorException(AuthenticationExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
