package com.seb44main011.petplaylist.domain.member.mapper;

import com.seb44main011.petplaylist.domain.member.dto.MemberDto;
import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.playlist.dto.PlaylistDto;
import com.seb44main011.petplaylist.domain.playlist.entity.entityTable.MusicList;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    Member memberDtoSignUpPostToMember(MemberDto.SignUpPost memberDtoSignUpPost);

    Member memberDtoLogInPostToMember(MemberDto.LogInPost memberDtoLogInPost);


    MemberDto.SignUpResponse memberToMemberDtoSignUpResponse(Member member);

    MemberDto.LogInResponse memberToMemberDtoLogInResponse(Member member);

    MemberDto.PatchResponse memberToMemberDtoPatchResponse(Member member);

    default MemberDto.MyPageResponse memberToMyPageResponse(Member member) {
        List<Music> musicList = new ArrayList<>();
        for (int i = 0; i < 6 ; i++) {
            if (member.getPersonalPlayList().getMusicLists().get(i).getMusic() == null) {
                break;
            }
            else musicList.add(member.getPersonalPlayList().getMusicLists().get(i).getMusic());
        }

        return MemberDto.MyPageResponse.builder()
                .email(member.getEmail())
                .name(member.getName())
                .musicLists(musicList)
                .build();
    }
}
