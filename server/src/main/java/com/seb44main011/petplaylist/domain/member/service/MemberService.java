package com.seb44main011.petplaylist.domain.member.service;

import com.seb44main011.petplaylist.domain.member.dto.MemberDto;
import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.member.repository.MemberRepository;
import com.seb44main011.petplaylist.domain.playlist.entity.entityTable.PersonalPlayList;
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
        member.updatePersonalPlayList(PersonalPlayList.builder().member(member).build());


        return memberRepository.save(member);
    }

    public Member updateMember(long memberId, MemberDto.Patch patchMember) {
        Member findMember = findVerifiedMember(memberId);
        isMemberActive(findMember);
        Optional.ofNullable(patchMember.getName())
                .ifPresent(findMember::updateName);
        Optional.ofNullable(patchMember.getProfile())
                .ifPresent(findMember::updateProfile);

        return memberRepository.save(findMember);
    }

    public void verifyExistsEmail(String email) {
        Optional<Member> findMembers = memberRepository.findByEmail(email);
        if (findMembers.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
    }

    public void isMemberActive(Member member) {
        if (member.getStatus().equals(Member.Status.MEMBER_QUIT)) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_WITHDRAWN);
        }
    }

    public Member findVerifiedMember(long memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);

        return findMember.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }
}
