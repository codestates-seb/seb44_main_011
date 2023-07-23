package com.seb44main011.petplaylist.domain.member.auth.handler.loginhandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.seb44main011.petplaylist.domain.member.dto.MemberDto;
import com.seb44main011.petplaylist.domain.member.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)  throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        Member member = setMemberInfo(response, authentication);
        printUserLog(member);
    }

    private static void printUserLog(Member member) {
        log.info("# Authenticated Successfully!");
        log.info("# 로그인 이메일: " + member.getEmail());
        log.info("# 로그인 멤버 ID " + member.getMemberId());
    }

    private static Member setMemberInfo(HttpServletResponse response, Authentication authentication) throws IOException {
        Member member = (Member) authentication.getPrincipal();
        httpResponseCreation(response, member);

        return member;
    }

    private static void httpResponseCreation(HttpServletResponse response, Member member) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        response.setHeader("Location", "/api/members" + member.getMemberId());
        MemberDto.LogInResponse logInResponse = MemberDto.LogInResponse.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .role(member.getEmail().equals("ADMIN@ADMIN.com") ? "ADMIN" : "USER")
                .build();

        response.getWriter().write(gson.toJson(logInResponse, MemberDto.LogInResponse.class));
    }
}
