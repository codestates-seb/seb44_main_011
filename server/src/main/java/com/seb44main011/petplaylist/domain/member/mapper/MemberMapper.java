package com.seb44main011.petplaylist.domain.member.mapper;

import com.seb44main011.petplaylist.domain.member.dto.MemberDto;
import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.playlist.dto.PlaylistDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    Member memberDtoSignUpPostToMember(MemberDto.SignUpPost memberDtoSignUpPost);

    Member memberDtoLogInPostToMember(MemberDto.LogInPost memberDtoLogInPost);


    MemberDto.SignUpResponse memberToMemberDtoSignUpResponse(Member member);

    MemberDto.LogInResponse memberToMemberDtoLogInResponse(Member member);

    MemberDto.PatchResponse memberToMemberDtoPatchResponse(Member member);

    default MemberDto.MyPageResponse memberToMyPageResponse(Member member) {
        List<PlaylistDto.PublicResponse> musicList = new ArrayList<>(6);

        if (!member.getPlayLists().isEmpty()) {
            for (int i = 0; i < 6; i++) {
                Music insertMusic = member.getPlayLists().get(i).getMusic();
                musicList.add(PlaylistDto.PublicResponse.builder()
                        .musicId(insertMusic.getMusicId())
                        .music_url(insertMusic.getMusic_url())
                        .image_url(insertMusic.getImage_url())
                        .playtime(insertMusic.getPlaytime())
                        .tags(insertMusic.getTags().getTags())
                        .title(insertMusic.getTitle())
                        .category(insertMusic.getCategory().getCategory())
                        .build());
            }
        }
        return MemberDto.MyPageResponse.builder()
                .email(member.getEmail())
                .name(member.getName())
                .musicLists(musicList)
                .build();
    }
}
