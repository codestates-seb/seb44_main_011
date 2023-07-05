package com.seb44main011.petplaylist.domain.member.service;

import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.member.repository.MemberRepository;
import com.seb44main011.petplaylist.global.error.BusinessLogicException;
import com.seb44main011.petplaylist.global.error.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());
        String passwordEncode = passwordEncoder.encode(member.getPassword());
        member.updatePassword(passwordEncode);

        return memberRepository.save(member);
    }

    private void verifyExistsEmail(String email) {
        Optional<Member> findMembers = memberRepository.findByEmail(email);
        if (findMembers.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.USER_EXISTS);
        }
    }
}
