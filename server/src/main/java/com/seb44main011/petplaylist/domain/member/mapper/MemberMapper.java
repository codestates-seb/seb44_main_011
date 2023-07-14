package com.seb44main011.petplaylist.domain.member.mapper;

import com.seb44main011.petplaylist.domain.member.dto.MemberDto;
import com.seb44main011.petplaylist.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    Member memberDtoSignUpPostToMember(MemberDto.SignUpPost memberDtoSignUpPost);

    Member memberDtoLogInPostToMember(MemberDto.LogInPost memberDtoLogInPost);

    Member memberDtoPatchToMember(MemberDto.Patch memberDtoPatch);

    MemberDto.SignUpResponse memberToMemberDtoSignUpResponse(Member member);

    MemberDto.LogInResponse memberToMemberDtoLogInResponse(Member member);

    MemberDto.PatchResponse memberToMemberDtoPatchResponse(Member member);
}
