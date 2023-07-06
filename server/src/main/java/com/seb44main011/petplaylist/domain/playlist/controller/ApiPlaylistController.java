package com.seb44main011.petplaylist.domain.playlist.controller;

import com.seb44main011.petplaylist.domain.music.dto.MusicDto;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.mapper.MusicMapper;
import com.seb44main011.petplaylist.domain.music.service.MusicService;
import com.seb44main011.petplaylist.domain.playlist.dto.PlaylistDto;
import com.seb44main011.petplaylist.domain.playlist.mapper.PlaylistMapper;
import com.seb44main011.petplaylist.domain.playlist.service.PlaylistService;
import com.seb44main011.petplaylist.global.utils.UriCreator;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;

@RestController
@RequestMapping("/api/playlist")
@Validated
@RequiredArgsConstructor
public class ApiPlaylistController {
    private final PlaylistMapper playlistMapper;
    private final MusicMapper musicMapper;
    private final PlaylistService playlistService;
    private final MusicService musicService;

    @PostMapping("/{member-id}")
    public ResponseEntity<?> postPersonalPlayList(@PathVariable("member-id")@Positive long id,
                                                  @RequestBody @Valid MusicDto.Post musicPost){
        Music music = musicMapper.musicToMusicPostDto(musicPost);
        playlistService.createPersonalMusicList(id,music);
        URI location = UriCreator.createUri("/api/playlist");
        return ResponseEntity.created(location).build();
    }


}
