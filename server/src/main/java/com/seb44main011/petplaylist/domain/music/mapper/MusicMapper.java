package com.seb44main011.petplaylist.domain.music.mapper;

import com.seb44main011.petplaylist.domain.music.dto.MusicDto;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MusicMapper {
    MusicDto.PublicResponse publicResponseToMusic(Music music);
    default MusicDto.ApiResponse apiResponseToMusic(Music music,boolean likes){
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
    Music musicToMusicPostDto(MusicDto.PostRequest musicDtoPostRequest);
}
