package com.seb44main011.petplaylist.domain.playlist.mapper;

import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.playlist.dto.PlaylistDto;
import com.seb44main011.petplaylist.domain.playlist.entity.entityTable.MusicList;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PlaylistMapper {
//    PlaylistDto.PublicResponse musicToCategoryPlayListResponse(Music music);
    default PlaylistDto.ApiResponse musicListToPlayListResponse(MusicList musicList){
        return PlaylistDto.ApiResponse.builder()
                .musicId(musicList.getMusic().getMusicId())
                .title(musicList.getMusic().getTitle())
                .music_url(musicList.getMusic().getMusic_url())
                .image_url(musicList.getMusic().getImage_url())
                .tags(musicList.getMusic().getTags().getTags())
                .category(musicList.getMusic().getCategory().getCategory())
                .liked(musicList.isLiked())
                .build();
    }
    List<PlaylistDto.ApiResponse> musicListToPlayListResponseList(List<MusicList> musicListList);



    List<PlaylistDto.PublicResponse> musicListToPublicResponse(List<Music> musicList);

    default List<PlaylistDto.ApiResponse> musicListToCategoryPlayListApiResponse(List<Music> musics, List<MusicList> musicListsList){
        Set<Long> musicIds = musicListsList.stream()
                .map(m->m.getMusic().getMusicId())
                .collect(Collectors.toSet());
        List<PlaylistDto.ApiResponse> apiResponse = musics.stream()
                .map(music ->{
                    boolean likes = musicIds.contains(music.getMusicId());
                   return PlaylistDto.ApiResponse.builder()
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

        return apiResponse;

    }

}
