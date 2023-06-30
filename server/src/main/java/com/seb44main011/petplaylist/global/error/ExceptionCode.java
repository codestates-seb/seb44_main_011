package com.seb44main011.petplaylist.global.error;

import lombok.Getter;

public enum ExceptionCode {

    ACCESS_DENIED(401,"This account is inaccessible"),
    INVALID_TOKEN(401,"is not the same token as data"),
    UNAUTHORIZED(401,"invalid token Data"),
    EXPIRED_TOKEN(401,"Token has expired"),
    ACCOUNT_RESTRICTED(403,"oauth account user"),
    USER_NOT_FOUND(404, "Member not found"),
    USER_EXISTS(409,"USER EXISTS"),
    USER_MISMATCH(403,"The login user and the author are different");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}