package com.seb44main011.petplaylist.domain.member.service;

import com.seb44main011.petplaylist.domain.member.dto.MemberDto;
import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.member.repository.MemberRepository;
import com.seb44main011.petplaylist.domain.music.service.storageService.S3Service;
import com.seb44main011.petplaylist.global.error.BusinessLogicException;
import com.seb44main011.petplaylist.global.error.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
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

    public Member updateMember(long memberId, MemberDto.Patch patchMember) {
        Member foundMember = findMember(memberId);
        Optional.ofNullable(patchMember.getName())
                .ifPresent(foundMember::updateName);
        foundMember.updateProfile(findProfileEnum(patchMember.getProfileUrl()));

        return memberRepository.save(foundMember);
    }

    public Member findByMemberFromEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        Member findMember = member.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND)
        );
        isMemberActive(findMember);

        return findMember;
    }

    public Member findMember(long memberId) {
        Member foundMember = findVerifiedMember(memberId);
        isMemberActive(foundMember);

        return foundMember;
    }

    public Member.Profile findProfileEnum(String profileUrl) {
        for (Member.Profile profile : Member.Profile.values()) {
            if (profile.getProfileUrl().equals(profileUrl)) return profile;
        }

        throw new BusinessLogicException(ExceptionCode.URL_NOT_FOUND);
    }

    public void disableMember(long memberId, String password) {
        Member foundMember = findMember(memberId);
        boolean matchPassword = passwordEncoder.matches(password, foundMember.getPassword());
        if (matchPassword) {
            foundMember.updateStatus(Member.Status.MEMBER_QUIT);
            memberRepository.save(foundMember);
        } else {
            throw new BusinessLogicException(ExceptionCode.PASSWORD_MISMATCH);
        }
    }

    public List<String> findProfileImage() {
        List<String> profileList = new ArrayList<>();
        for (Member.Profile profile : Member.Profile.values()) {
            profileList.add(profile.getProfileUrl());
        }

        return profileList;
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

//    public void uploadMemberProfileImage(@RequestPart MultipartFile profileImage) {
//        Member byEmail = memberRepository.findByEmail(email).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
//        Member targetMember = findMember(byEmail.getMemberId());
//        targetMember.setProfile(profileImage.getOriginalFilename());
//        memberRepository.save(targetMember);
//    }
}
