package com.seb44main011.petplaylist.domain.playlist.controller;

import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.service.mainService.MusicService;
import com.seb44main011.petplaylist.domain.playlist.dto.PlaylistDto;
import com.seb44main011.petplaylist.domain.playlist.mapper.MusicListMapper;
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
    private final MusicListMapper mapper;
    private final MusicService musicService;

    @GetMapping
    public ResponseEntity<?> getAllMusicListFromNonMember(@RequestParam(value = "page", defaultValue = "1") int page,
                                                          @RequestParam(value = "sort", required = false,defaultValue = "view") String sortValue){
        Page<Music> musicPage = musicService.findMusicListAll(page,sortValue);
        List<Music> musicList = musicPage.getContent();
        List<PlaylistDto.PublicResponse> publicResponses = mapper.musicListToPublicResponse(musicList);

        return ResponseEntity.ok().body(
                new MultiResponseDto<>(publicResponses,musicPage)
        );
    }
    @GetMapping(value = "/{dogOrCats}",params = {"page"})
    public ResponseEntity<?> getMusicListByCategoryAndTags(@PathVariable String dogOrCats
                                                        , @RequestParam(value = "page", defaultValue = "1") int page
                                                        , @RequestParam(value = "tags",required = false)String tags){
        Music.Category category = Music.Category.valueOf(dogOrCats.toUpperCase());
        Page<Music> musicPage = musicService.findCategoryAndTagsPageMusic(category,tags,page);
        List<Music> musicList = musicPage.getContent();
        List<PlaylistDto.PublicResponse> publicResponses = mapper.musicListToPublicResponse(musicList);

        return new ResponseEntity(
                new MultiResponseDto<>(publicResponses,musicPage), HttpStatus.OK);

    }
}
