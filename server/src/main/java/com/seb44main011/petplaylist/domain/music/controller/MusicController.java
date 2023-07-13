package com.seb44main011.petplaylist.domain.music.controller;

import com.seb44main011.petplaylist.domain.music.dto.MusicDto;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.mapper.MusicMapper;
import com.seb44main011.petplaylist.domain.music.service.MusicService;
import com.seb44main011.petplaylist.global.common.AuthenticationName;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;



//@Validated
//@RequiredArgsConstructor
//public  class MusicController {
//    private final MusicMapper mapper;
//    private final MusicService service;
//
//    @RestController
//    @RequestMapping("/public")
//    public  class PublicController{
//        @GetMapping(value = "/musics",params = {"music_name"})
//        public ResponseEntity<?> getPublicMusicByTitle(@RequestParam(name = "music_name")String musicTitle){
//            MusicDto.PublicResponse response= mapper.publicResponseToMusic(findMusicByTitle(musicTitle));
//            return ResponseEntity.ok().body(response);
//        }
//        @GetMapping(value = "/musics",params = {"music_id"})
//        public ResponseEntity<?> getPublicMusicById(@RequestParam(name = "music_id")@Positive long musicId){
//            MusicDto.PublicResponse response= mapper.publicResponseToMusic(findMusicById(musicId));
//            return ResponseEntity.ok().body(response);
//        }
//    }
//    @RestController
//    @RequestMapping("/api")
//    public  class ApiController{
//        @GetMapping(value = "/musics",params = {"music_name"})
//        public ResponseEntity<?> getApiMusicByTitle(@AuthenticationName String email, @RequestParam(name = "music_name")String musicTitle){
//            MusicDto.ApiResponse response= mapper.apiResponseToMusic(findMusicByTitle(musicTitle));
//            return ResponseEntity.ok().body(response);
//        }
//        @GetMapping(value = "/musics",params = {"music_id"})
//        public ResponseEntity<?> getPublicMusicById(@AuthenticationName String email, @RequestParam(name = "music_id")@Positive long musicId){
//            MusicDto.PublicResponse response= mapper.publicResponseToMusic(findMusicById(musicId));
//            return ResponseEntity.ok().body(response);
//        }
//
//    }
//
//
//
//
//
//
//
//
//
//    //이름으로 찾기
//    private Music findMusicByTitle(String musicTitle){
//        return service.findMusic(musicTitle);
//    }
//
//    //아이디로 찾기
//    private Music findMusicById(long MusicId){
//        return service.findMusic(MusicId);
//    }
//
//}
