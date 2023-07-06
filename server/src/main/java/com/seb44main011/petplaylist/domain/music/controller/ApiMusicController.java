package com.seb44main011.petplaylist.domain.music.controller;

import com.seb44main011.petplaylist.domain.music.dto.MusicDto;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.mapper.MusicMapper;
import com.seb44main011.petplaylist.domain.music.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ApiMusicController {
    private final MusicMapper mapper;
    private final MusicService service;
    @GetMapping(value = "/musics",params = {"music_name"})
    public ResponseEntity<?> getPublicMusicByTitle(@RequestParam(name = "music_name")String musicTitle){
        Music findMusic = service.serchMusic(musicTitle);
        MusicDto.ApiResponse response= mapper.apiResponseToMusic(findMusic);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping(value = "/musics",params = {"music_id"})
    public ResponseEntity<?> getPublicMusicById(@RequestParam(name = "music_id")@Positive long musicId){
        Music findMusic = service.serchMusic(musicId);
        MusicDto.ApiResponse response= mapper.apiResponseToMusic(findMusic);
        return ResponseEntity.ok().body(response);
    }
}
