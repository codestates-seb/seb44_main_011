package com.seb44main011.petplaylist.domain.member.auth.handler.authErrorHandler;

import com.seb44main011.petplaylist.domain.member.auth.util.responder.AuthenticationErrorResponder;
import com.seb44main011.petplaylist.global.error.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class MemberAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ExceptionCode exception = (ExceptionCode) request.getAttribute("exception");
        if (exception == null) {
            log.warn("Unauthorized error happened: {}", HttpStatus.UNAUTHORIZED.value());
            AuthenticationErrorResponder.sendErrorResponse(response, HttpStatus.UNAUTHORIZED);
        }
        else {
            String errormessage = exception.getMessage();
            AuthenticationErrorResponder.sendErrorResponse(response, HttpStatus.UNAUTHORIZED, errormessage);
            log.warn("Unauthorized error happened: {}", exception.getMessage());
        }
    }
}
