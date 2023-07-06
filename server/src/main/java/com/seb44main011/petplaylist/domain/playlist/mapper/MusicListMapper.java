package com.seb44main011.petplaylist.domain.playlist.mapper;

import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.playlist.entity.entityTable.MusicList;
import com.seb44main011.petplaylist.domain.playlist.entity.entityTable.PersonalPlayList;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MusicListMapper {
    default MusicList MemberAndMusicToMusicList(PersonalPlayList playList, Music music){
        return MusicList.builder()
                .personalPlayList(playList)
                .music(music)
                .build();
    }
}
