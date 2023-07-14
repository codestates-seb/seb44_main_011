package com.seb44main011.petplaylist.domain.playlist.mapper;

import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.playlist.dto.PlaylistDto;
import com.seb44main011.petplaylist.domain.playlist.entity.entityTable.PlayList;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MusicListMapper {
    default PlayList memberAndMusicToMusicList(Member member, Music music){
        return PlayList.builder()
                .member(member)
                .music(music)
                .build();
    }
    default PlaylistDto.ApiResponse musicListToPlayListResponse(PlayList playList){
        return PlaylistDto.ApiResponse.builder()
                .musicId(playList.getMusic().getMusicId())
                .title(playList.getMusic().getTitle())
                .music_url(playList.getMusic().getMusic_url())
                .image_url(playList.getMusic().getImage_url())
                .playtime(playList.getMusic().getPlaytime())
                .tags(playList.getMusic().getTags().getTags())
                .category(playList.getMusic().getCategory().getCategory())
                .liked(playList.isLiked())
                .build();
    }
    List<PlaylistDto.ApiResponse> musicListToPlayListResponseList(List<PlayList> playListList);



    List<PlaylistDto.PublicResponse> musicListToPublicResponse(List<Music> musicList);

    default List<PlaylistDto.ApiResponse> musicListToApiResponse(List<Music> musics, List<PlayList> playListsList){
        Set<Long> musicIds = playListsList.stream()
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
                            .playtime(music.getPlaytime())
                            .tags(music.getTags().getTags())
                            .liked(likes)
                            .build();

                })
                .collect(Collectors.toList());

        return apiResponse;

    }

    default List<PlaylistDto.ApiResponse> musicListToApiResponseFromId(List<Music> musics,int memberId){
        List<PlaylistDto.ApiResponse> apiResponse = musics.stream()
                .map(music ->{
                    return PlaylistDto.ApiResponse.builder()
                            .musicId(music.getMusicId())
                            .category(music.getCategory().getCategory())
                            .title(music.getTitle())
                            .music_url(music.getMusic_url())
                            .image_url(music.getImage_url())
                            .playtime(music.getPlaytime())
                            .tags(music.getTags().getTags())
                            .liked(music.getPersonalPlayLists().stream()
                                    .anyMatch(m->m.getMusic().getMusicId()==memberId))
                            .build();

                })
                .collect(Collectors.toList());

        return apiResponse;

    }
}
