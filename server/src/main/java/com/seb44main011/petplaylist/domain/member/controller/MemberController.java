package com.seb44main011.petplaylist.domain.member.controller;

import com.seb44main011.petplaylist.domain.member.dto.MemberDto;
import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.member.mapper.MemberMapper;
import com.seb44main011.petplaylist.domain.member.service.MemberService;
import com.seb44main011.petplaylist.global.common.SingleResponseDto;
import com.seb44main011.petplaylist.global.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;

@RestController
@Validated
@RequiredArgsConstructor
public class MemberController {
    private final MemberMapper memberMapper;
    private final MemberService memberService;


    @PostMapping("/public/signup")
    public ResponseEntity postSignUp(@Valid
                                     @RequestBody MemberDto.SignUpPost signUpPost) {
        Member member = memberMapper.memberDtoSignUpPostToMember(signUpPost);
        Member createdMember = memberService.createMember(member);
        URI location = UriCreator.createUri("/api/members",createdMember.getMemberId());


        return ResponseEntity.created(location).body(memberMapper.memberToMemberDtoSignUpResponse(createdMember));
    }

    @PatchMapping("/api/members/{member-id}")
    public ResponseEntity patchMember(@Valid
                                      @PathVariable("member-id") @Positive long memberId,
                                      @RequestBody MemberDto.Patch patchMember) {
        Member updateMember = memberService.updateMember(memberId, patchMember);
        URI location = UriCreator.createUri("/api/members", updateMember.getMemberId());

        return ResponseEntity.ok().location(location).body(memberMapper.memberToMemberDtoPatchResponse(updateMember));
    }

    @GetMapping("/api/members/{member-id}")
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
}
