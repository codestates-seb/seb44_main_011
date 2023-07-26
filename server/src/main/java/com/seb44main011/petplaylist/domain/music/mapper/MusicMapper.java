package com.seb44main011.petplaylist.domain.music.mapper;

import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.music.dto.MusicDto;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MusicMapper {
    MusicDto.PublicResponse publicResponseToMusic(Music music);
    default MusicDto.ApiResponse apiResponseToMusic(Music music, Member member){
        boolean likes = member.getPlayLists().stream()
                .anyMatch(musicList -> Objects.equals(musicList.getMusic().getMusicId(), music.getMusicId()));
        return MusicDto.ApiResponse.builder()
                .image_url(music.getImage_url())
                .music_url(music.getMusic_url())
                .musicId(music.getMusicId())
                .playtime(music.getPlaytime())
                .title(music.getTitle())
                .liked(likes)
                .tags(music.getTags().getTags())
                .build();
    }
    default Music musicToMusicPostDto(MusicDto.PostMusicFile postMusicFile){
        return Music.builder()
                .tags(postMusicFile.getTags())
                .title(postMusicFile.getTitle())
                .category(postMusicFile.getCategory())
                .build();
    }
    MusicDto.AdminResponse musicToAdminResponse(Music music);
    List<MusicDto.AdminResponse> musicToAdminResponseList(List<Music> musicList);
}
