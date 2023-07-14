package com.seb44main011.petplaylist.domain.music.controller;

import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.member.service.MemberService;
import com.seb44main011.petplaylist.domain.music.dto.MusicDto;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.mapper.MusicMapper;
import com.seb44main011.petplaylist.domain.music.service.mainService.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.Objects;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ApiMusicController {
    private final MusicMapper mapper;
    private final MusicService service;
    private final MemberService memberService;

    //TODO: 시큐리티 적용 시 구현 수정

    @GetMapping(value = "/musics/{member-id}",params = {"music_name"})
    public ResponseEntity<?> getApiMusicByTitle(@RequestParam(name = "music_name")String musicTitle,
                                                @PathVariable("member-id") @Positive long memberId){
        Music findMusic = service.serchMusic(musicTitle);
        Member findMember = memberService.findVerifiedMember(memberId);
        MusicDto.ApiResponse response= mapper.apiResponseToMusic(findMusic,findMember);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping(value = "/musics/{member-id}",params = {"music_id"})
    public ResponseEntity<?> getApiMusicById(@RequestParam(name = "music_id")@Positive long musicId,
                                             @PathVariable("member-id") @Positive long memberId){
        Music findMusic = service.serchMusic(musicId);
        Member findMember = memberService.findVerifiedMember(memberId);
        MusicDto.ApiResponse response= mapper.apiResponseToMusic(findMusic,findMember);
        return ResponseEntity.ok().body(response);
    }
}
