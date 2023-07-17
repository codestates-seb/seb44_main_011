package com.seb44main011.petplaylist.domain.member.auth.handler.loginhandler;

import com.google.gson.Gson;
import com.seb44main011.petplaylist.global.common.ErrorResponse;
import com.seb44main011.petplaylist.global.error.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class  UserAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private static final String IssueAtAuthenticationProvider = HttpStatus.UNAUTHORIZED.getReasonPhrase();
    private static final String InvalidCredentialsMessage = "Invalid credentials";


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        log.error("# Authentication Failed..: {}", exception.getMessage());
        log.error("# Authentication Failed Exception Classname: {}", exception.getClass().getName());
        Gson gson = new Gson();

        sendErrorResponse(response, gson, exception);
    }

    private static void sendErrorResponse(HttpServletResponse response, Gson gson, AuthenticationException exception) throws IOException {
        String exceptionMessage = exception.getClass().getName().equals(BadCredentialsException.class.getName())
                ? InvalidCredentialsMessage
                : exception.getMessage();
        ErrorResponse errorResponse = getErrorResponse(exceptionMessage, response);

        response.getWriter().write(gson.toJson(errorResponse, ErrorResponse.class));
    }

    private static ErrorResponse getErrorResponse(String exceptionMessage, HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        if (exceptionMessage == null || exceptionMessage.equals(IssueAtAuthenticationProvider)) {
            return ErrorResponse.of(HttpStatus.UNAUTHORIZED);

        } else if (exceptionMessage.equals(ExceptionCode.ACCOUNT_RESTRICTED.getMessage())) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return ErrorResponse.of(HttpStatus.FORBIDDEN, exceptionMessage);
        }

        return ErrorResponse.of(HttpStatus.UNAUTHORIZED, exceptionMessage + " : " +
                HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }
}
