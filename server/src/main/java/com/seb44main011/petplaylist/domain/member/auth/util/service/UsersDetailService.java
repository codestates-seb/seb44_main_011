package com.seb44main011.petplaylist.domain.member.auth.util.service;

import com.seb44main011.petplaylist.domain.member.auth.util.userdetail.UserDetail;
import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.member.repository.MemberRepository;
import com.seb44main011.petplaylist.global.error.BusinessLogicException;
import com.seb44main011.petplaylist.global.error.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class UsersDetailService implements UserDetailsService {
    private final MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("UserDetailService in username: {}",username);
        Optional<Member> optionalMember = repository.findByEmail(username);
        Member member = optionalMember.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND)
        );
        checkUser(member);

        return new UserDetail(member);
    }

    private void checkUser(Member member) {
        if (member.getStatus().equals(Member.Status.MEMBER_QUIT)) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_WITHDRAWN);
        } else if (!member.getOAuthCheck().equals(Member.OAuthCheck.NO_OAUTH)) {
            throw new BusinessLogicException(ExceptionCode.ALREADY_OAUTH_MEMBER);
        }
    }
}
