package com.seb44main011.petplaylist.domain.playlist.controller;

import com.seb44main011.petplaylist.domain.music.dto.MusicDto;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.mapper.MusicMapper;
import com.seb44main011.petplaylist.domain.music.service.MusicService;
import com.seb44main011.petplaylist.domain.playlist.dto.PlaylistDto;
import com.seb44main011.petplaylist.domain.playlist.mapper.PlaylistMapper;
import com.seb44main011.petplaylist.domain.playlist.service.PlaylistService;
import com.seb44main011.petplaylist.global.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;

@RestController
@RequestMapping("/api/playlist")
@Slf4j
@RequiredArgsConstructor
public class ApiPlaylistController {
    private final PlaylistMapper playlistMapper;
    private final MusicMapper musicMapper;
    private final PlaylistService playlistService;
    private final MusicService musicService;

    @PostMapping(value = "/{member-id}", name = "music_name")
    public ResponseEntity<?> postPersonalPlayList(@PathVariable("member-id")@Positive long id,
                                                  @Valid @RequestBody MusicDto.Post post){
        playlistService.createPersonalMusicList(id,post.getMusicId());
        URI location = UriCreator.createUri("/api/playlist");
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/{member-id}", name = "music_name")
    public ResponseEntity<?> deletePersonalPlayList(@PathVariable("member-id")@Positive long id,
                                                  @Valid @RequestBody MusicDto.Post post){
        playlistService.deletePersonalMusicList(id,post.getMusicId());
        URI location = UriCreator.createUri("/api/playlist");
        return ResponseEntity.created(location).build();
    }


}
