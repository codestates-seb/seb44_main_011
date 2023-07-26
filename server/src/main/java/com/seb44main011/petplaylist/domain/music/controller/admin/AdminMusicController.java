package com.seb44main011.petplaylist.domain.music.controller.admin;

import com.seb44main011.petplaylist.domain.member.service.MemberService;
import com.seb44main011.petplaylist.domain.music.dto.MusicDto;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.mapper.MusicMapper;
import com.seb44main011.petplaylist.domain.music.service.mainService.MusicService;
import com.seb44main011.petplaylist.global.common.AuthenticationName;
import com.seb44main011.petplaylist.global.common.MultiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/music")
public class AdminMusicController {
    private final MemberService memberService;
    private final MusicService musicService;
    private final MusicMapper mapper;
    @Value("${User.mail.admin}")
    private String ADMIN_EMAIL;

    @GetMapping("/id/{music-id}")
    public ResponseEntity<?> getMusic(@AuthenticationName String email,
                                      @PathVariable("music-id") @Positive long musicId){
        memberService.findByMemberFromEmail(email);
        Music music = musicService.findMusicAnyStatus(musicId);
        MusicDto.AdminResponse adminResponse = mapper.musicToAdminResponse(music);

        return ResponseEntity.ok(adminResponse);
    }
    @GetMapping("/title/{title}")
    public ResponseEntity<?> getMusic(@AuthenticationName String email,
                                      @PathVariable("title") String title){
        memberService.findByMemberFromEmail(email);
        Music music = musicService.findMusicAnyStatus(title);
        MusicDto.AdminResponse adminResponse = mapper.musicToAdminResponse(music);

        return ResponseEntity.ok(adminResponse);
    }
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllMusicList(@AuthenticationName String email,
                                             @RequestParam(value = "page",required = false,defaultValue = "1") @Positive int page,
                                             @RequestParam(value = "size",required = false,defaultValue = "6") @Positive int size,
                                             @RequestParam(value = "sort",required = false,defaultValue = "view") String sort){
        memberService.findByMemberFromEmail(email);
        Page<Music> musicPage = musicService.findMusicListAllFromAdmin(page,size,sort);
        List<MusicDto.AdminResponse> apiResponseList = mapper.musicToAdminResponseList(musicPage.getContent());

        return ResponseEntity.ok(new MultiResponseDto<>(apiResponseList,musicPage));
    }
    @GetMapping(value = "/inactive")
    public ResponseEntity<?> getAllInactiveMusicList(@AuthenticationName String email,
                                             @RequestParam(value = "page",required = false,defaultValue = "1") @Positive int page,
                                             @RequestParam(value = "size",required = false,defaultValue = "6") @Positive int size,
                                             @RequestParam(value = "sort",required = false,defaultValue = "view") String sort){
        memberService.findByMemberFromEmail(email);
        Page<Music> musicPage = musicService.findMusicListAllFromAdminByStatus(Music.Status.INACTIVE,page,size,sort);
        List<MusicDto.AdminResponse> adminResponses = mapper.musicToAdminResponseList(musicPage.getContent());

        return ResponseEntity.ok(new MultiResponseDto<>(adminResponses,musicPage));
    }
    @GetMapping(value = "/active")
    public ResponseEntity<?> getAllActiveMusicList(@AuthenticationName String email,
                                                     @RequestParam(value = "page",required = false,defaultValue = "1") @Positive int page,
                                                     @RequestParam(value = "size",required = false,defaultValue = "6") @Positive int size,
                                                     @RequestParam(value = "sort",required = false,defaultValue = "view") String sort){
        memberService.findByMemberFromEmail(email);
        Page<Music> musicPage = musicService.findMusicListAllFromAdminByStatus(Music.Status.ACTIVE,page,size,sort);
        List<MusicDto.AdminResponse> adminResponses = mapper.musicToAdminResponseList(musicPage.getContent());

        return ResponseEntity.ok(new MultiResponseDto<>(adminResponses,musicPage));
    }
}
