package com.seb44main011.petplaylist.domain.member.auth.util.error;

import lombok.Getter;

public enum  AuthenticationExceptionCode {
    MEMBER_CONFLICT(409, "MEMBER_CONFLICT");
    @Getter
    private int status;

    @Getter
    private String message;

    AuthenticationExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
