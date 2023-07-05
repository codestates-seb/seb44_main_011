package com.seb44main011.petplaylist.domain.playlist.mapper;

import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.playlist.entity.MusicList;
import com.seb44main011.petplaylist.domain.playlist.entity.PersonalPlayList;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PlaylistMapper {
   default MusicList MemberAndMusicToMusicList(PersonalPlayList playList, Music music){
       return MusicList.builder()
               .personalPlayList(playList)
               .music(music)
               .build();
   }
}
