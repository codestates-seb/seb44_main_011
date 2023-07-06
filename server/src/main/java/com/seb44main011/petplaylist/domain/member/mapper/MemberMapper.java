package com.seb44main011.petplaylist.domain.member.mapper;

import com.seb44main011.petplaylist.domain.member.dto.MemberDto;
import com.seb44main011.petplaylist.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    Member memberDtoSignUpPostToMember(MemberDto.SignUpPost memberDtoSignUpPost);

    Member memberDtoLogInPostToMember(MemberDto.LogInPost memberDtoLogInPost);

    Member memberDtoPatchToMember(MemberDto.Patch memberDtoPatch);

    MemberDto.Response memberToMemberDtoResponse(Member member);
}
