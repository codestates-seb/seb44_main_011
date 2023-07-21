package com.seb44main011.petplaylist.domain.member.controller;

import com.seb44main011.petplaylist.domain.member.dto.MemberDto;
import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.member.mapper.MemberMapper;
import com.seb44main011.petplaylist.domain.member.service.MemberService;
import com.seb44main011.petplaylist.domain.music.service.storageService.S3Service;
import com.seb44main011.petplaylist.global.common.AuthenticationName;
import com.seb44main011.petplaylist.global.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
public class MemberController {
    private final MemberMapper memberMapper;
    private final MemberService memberService;
    private final S3Service s3Service;


    @PostMapping("/public/signup")
    public ResponseEntity postSignUp(@Valid
                                     @RequestBody MemberDto.SignUpPost signUpPost) {
        Member member = memberMapper.memberDtoSignUpPostToMember(signUpPost);
        Member createdMember = memberService.createMember(member);
        URI location = UriCreator.createUri("/api/members",createdMember.getMemberId());

        return ResponseEntity.created(location).body(memberMapper.memberToMemberDtoSignUpResponse(createdMember));
    }

    @PatchMapping("/api/members/my-page/{member-id}")
    public ResponseEntity patchMember(@Valid
                                      @PathVariable("member-id") @Positive long memberId,
                                      @RequestBody MemberDto.Patch patchMember) {
        Member updateMember = memberService.updateMember(memberId, patchMember);
        URI location = UriCreator.createUri("/api/members", updateMember.getMemberId());

        return ResponseEntity.ok().location(location).body(memberMapper.memberToMemberDtoPatchResponse(updateMember));
    }

    @GetMapping("/api/members/my-page/{member-id}")
    public ResponseEntity getMyPage(@Valid
                                    @PathVariable("member-id") @Positive long memberId) {
        Member findMember = memberService.findMember(memberId);
        MemberDto.MyPageResponse myPageResponse = memberMapper.memberToMyPageResponse(findMember);
        URI location = UriCreator.createUri("/api/members", findMember.getMemberId());

        return ResponseEntity.ok().location(location).body(myPageResponse);
    }

    @DeleteMapping("/api/members/{member-id}")
    public ResponseEntity deleteMember(@Valid
                                       @PathVariable("member-id") @Positive long memberId,
                                       @RequestBody MemberDto.Delete deleteMember) {
        memberService.disableMember(memberId, deleteMember.getPassword());
        URI location = UriCreator.createUri("/public/signup");

        return ResponseEntity.ok().location(location).build();
    }

    @GetMapping(value = "/api/members/my-page/profiles")
    public ResponseEntity getProfileImage() {
        return ResponseEntity.ok(memberService.findProfileImage());
    }

//    @PostMapping(value = "/api/members/{member-id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity setMemberProfile(@AuthenticationName String email, @RequestPart(value = "file") MultipartFile profileImage) {
//        log.info("profileImage : {}", profileImage.getOriginalFilename());
//        memberService.setMemberProfileImage(email, profileImage);
//
//        return ResponseEntity.ok().build();
//    }
}
