package com.seb44main011.petplaylist.domain.playlist.mapper;

import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.playlist.dto.PlaylistDto;
import com.seb44main011.petplaylist.domain.playlist.entity.entityTable.MusicList;
import com.seb44main011.petplaylist.domain.playlist.entity.entityTable.PersonalPlayList;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PlaylistMapper {
//    PlaylistDto.PublicCategoryPlayListResponse musicToCategoryPlayListResponse(Music music);

    PersonalPlayList personalPlayListToMemberId(Long personalPlayListId);
    List<PlaylistDto.PublicCategoryPlayListResponse> musicListToCategoryPlayListPublicResponse(List<Music> musicList);

    default List<PlaylistDto.ApiCategoryPlayListResponse> musicListToCategoryPlayListApiResponse(List<Music> musics, List<MusicList> musicListsList){
        Set<Long> musicIds = musicListsList.stream()
                .map(m->m.getMusic().getMusicId())
                .collect(Collectors.toSet());
        List<PlaylistDto.ApiCategoryPlayListResponse> apiCategoryPlayListResponses = musics.stream()
                .map(music ->{
                    boolean likes = musicIds.contains(music.getMusicId());
                   return PlaylistDto.ApiCategoryPlayListResponse.builder()
                                .musicId(music.getMusicId())
                                .category(music.getCategory().getCategory())
                                .title(music.getTitle())
                                .music_url(music.getMusic_url())
                                .image_url(music.getImage_url())
                                .tags(music.getTags().getTags())
                                .liked(likes)
                            .build();

                })
                .collect(Collectors.toList());

        return apiCategoryPlayListResponses;

    }

}
