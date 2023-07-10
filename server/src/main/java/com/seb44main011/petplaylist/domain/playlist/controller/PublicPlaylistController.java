package com.seb44main011.petplaylist.domain.playlist.controller;

import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.mapper.MusicMapper;
import com.seb44main011.petplaylist.domain.music.service.MusicService;
import com.seb44main011.petplaylist.domain.playlist.dto.PlaylistDto;
import com.seb44main011.petplaylist.domain.playlist.mapper.PlaylistMapper;
import com.seb44main011.petplaylist.global.common.MultiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/playlist")
@RequiredArgsConstructor
public class PublicPlaylistController {
    private final PlaylistMapper playlistMapper;
    private final MusicMapper musicMapper;
    private final MusicService musicService;
    @GetMapping(value = "/{dogOrCats}",params = {"page"})
    public ResponseEntity<?> getCategoryAndTagsPlayList(@PathVariable String dogOrCats
                                                        ,@RequestParam(value = "page", defaultValue = "1") int page
                                                        ,@RequestParam(value = "tags",required = false)String tags){
        Music.Category category = Music.Category.valueOf(dogOrCats.toUpperCase());
        Page<Music> musicPage = musicService.findCategoryAndTagsPageMusic(category,tags,page);
        List<Music> musicList = musicPage.getContent();
        List<PlaylistDto.PublicCategoryPlayListResponse> playListResponses = playlistMapper.musicListToCategoryPlayListPublicResponse(musicList);

        return new ResponseEntity(
                new MultiResponseDto<>(playListResponses,musicPage), HttpStatus.OK);

    }
}
