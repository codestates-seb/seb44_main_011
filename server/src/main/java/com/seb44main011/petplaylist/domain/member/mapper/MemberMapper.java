package com.seb44main011.petplaylist.domain.member.mapper;

import com.seb44main011.petplaylist.domain.member.dto.MemberDto;
import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.playlist.dto.PlaylistDto;
import com.seb44main011.petplaylist.domain.playlist.entity.entityTable.PlayList;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    Member memberDtoSignUpPostToMember(MemberDto.SignUpPost memberDtoSignUpPost);


    MemberDto.SignUpResponse memberToMemberDtoSignUpResponse(Member member);

    default MemberDto.PatchResponse memberToMemberDtoPatchResponse(Member member) {
        return MemberDto.PatchResponse.builder()
                .email(member.getEmail())
                .name(member.getName())
                .profileUrl(member.getProfile().getProfileUrl())
                .build();
    }

    default MemberDto.MyPageResponse memberToMyPageResponse(Member member) {
        List<PlaylistDto.PublicResponse> musicList = new ArrayList<>();
        List<PlayList> memberPlayList = member.getPlayLists();
        int memberPlayListSize = memberPlayList.size();
        if ( memberPlayListSize > 6) {
            memberPlayListSize = 6;
        }
        if (!memberPlayList.isEmpty()) {
            for (int i = 0; i < memberPlayListSize; i++) {
                Music insertMusic = member.getPlayLists().get(i).getMusic();
                if (insertMusic == null) {
                    break;
                }
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
                .profileUrl(member.getProfile().getProfileUrl())
                .musicLists(musicList)
                .build();
    }
}
