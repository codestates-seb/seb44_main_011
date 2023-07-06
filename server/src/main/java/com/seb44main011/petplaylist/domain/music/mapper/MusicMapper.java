package com.seb44main011.petplaylist.domain.music.mapper;

import com.seb44main011.petplaylist.domain.music.dto.MusicDto;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MusicMapper {
    MusicDto.PublicResponse publicResponseToMusic(Music music);
    MusicDto.ApiResponse apiResponseToMusic(Music music);

    Music musicToMusicPostDto(MusicDto.Post musicDtoPost);
}
