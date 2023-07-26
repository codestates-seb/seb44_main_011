package com.seb44main011.petplaylist.domain.member.auth.handler.oauthHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.seb44main011.petplaylist.domain.member.auth.handler.loginhandler.UserAuthenticationFailureHandler;
import com.seb44main011.petplaylist.domain.member.auth.util.error.AuthenticationExceptionCode;
import com.seb44main011.petplaylist.global.common.ErrorResponse;
import com.seb44main011.petplaylist.global.error.ExceptionCode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.gson.GsonBuilderCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
public class OAuth2FailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("# Authentication Failed..: {}", exception.getMessage());
        log.error("# Authentication Failed Exception Classname: {}", exception.getClass().getName());

        redirect(request, response,exception);

    }
    @SneakyThrows
    private void redirect(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String errorMessage = exception.getMessage().equals(AuthenticationExceptionCode.MEMBER_CONFLICT.getMessage()) ?
                exception.getMessage() : "BAD_REQUEST";
        ErrorResponse errorResponse = getErrorResponse(errorMessage, response);
        response.getWriter().write(gson.toJson(errorResponse, ErrorResponse.class));

        log.error("# errorMessage..: {}", errorMessage);
        String redirectURL=createURI(exception).toString();
        getRedirectStrategy().sendRedirect(request, response, redirectURL);
    }

    private URI createURI(AuthenticationException exception) throws URISyntaxException {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("error_message", exception.getMessage());

        return UriComponentsBuilder // 로컬 테스트용
                .newInstance()
                .scheme("https")
                .host("on.petpil.site")
                .path("/oauth")
                .queryParams(queryParams)
                .build()
                .toUri();
    }
//    return UriComponentsBuilder // 로컬 테스트용
//            .newInstance()
//            .scheme("http")
//                .host("localhost")
//                .port(3000)
//                .path("/oAuht")
//                .queryParams(queryParams)
//                .build()
//                .toUri();

    private static ErrorResponse getErrorResponse(String exceptionMessage, HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        if (exceptionMessage.equals(AuthenticationExceptionCode.MEMBER_CONFLICT.getMessage())) {
            response.setStatus(HttpStatus.CONFLICT.value());

            return ErrorResponse.of(HttpStatus.CONFLICT, exceptionMessage);
        }
        log.info("알 수 없는 에러 입니다. : {}",exceptionMessage);
        return ErrorResponse.of(HttpStatus.UNAUTHORIZED, exceptionMessage + " : " +
                "unKnownMessage_Data");
    }
}

