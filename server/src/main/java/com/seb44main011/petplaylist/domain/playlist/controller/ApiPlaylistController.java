package com.seb44main011.petplaylist.domain.playlist.controller;

import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.member.service.MemberService;
import com.seb44main011.petplaylist.domain.music.dto.MusicDto;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.mapper.MusicMapper;
import com.seb44main011.petplaylist.domain.music.service.mainService.MusicService;
import com.seb44main011.petplaylist.domain.playlist.dto.PlaylistDto;
import com.seb44main011.petplaylist.domain.playlist.entity.entityTable.PlayList;
import com.seb44main011.petplaylist.domain.playlist.mapper.MusicListMapper;
import com.seb44main011.petplaylist.domain.playlist.service.MusicListService;
import com.seb44main011.petplaylist.global.common.AuthenticationName;
import com.seb44main011.petplaylist.global.common.MultiResponseDto;
//import com.seb44main011.petplaylist.global.stubData.StubData;
import com.seb44main011.petplaylist.global.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/playlist")
@Slf4j
@RequiredArgsConstructor
public class ApiPlaylistController {
    //:TODO 토큰 정보 활용으로 변경 URL 도 변경 예정임
    private final MusicMapper musicMapper;
    private final MusicListMapper musicListMapper;

    private final MemberService memberService;
    private final MusicListService musicListService;
    private final MusicService musicService;
//    private final StubData stubData;
//    @PostMapping("/test")
//    public void postTest() throws InterruptedException {
//        stubData.insertData();
//    }

    @PostMapping(value = "/{member-id}", name = "music_name")
    public ResponseEntity<?> postPersonalPlayList(@PathVariable("member-id")@Positive long memberId,
                                                  @Valid @RequestBody MusicDto.PostRequest postRequest,
                                                  @AuthenticationName String email){
        musicListService.updatePlayList(postRequest,email,memberId);
        URI location = UriCreator.createUri("/api/playlist");

        return ResponseEntity.created(location).build();
    }

    @GetMapping(params = {"member-id","page"})
    public ResponseEntity<?> getAllMusicListFromMember(@RequestParam(name = "member-id") @Positive int memberId,
                                                       @RequestParam(name = "page", defaultValue = "1") @Positive int page,
                                                       @AuthenticationName String email) {
        List<PlayList> memberPlayList = musicListService.findPersonalMusicLists(email);
        Page<Music> musicPage= musicService.findMusicListAll(page);
        List<Music> musicList = musicPage.getContent();
        List<PlaylistDto.ApiResponse> responseMusic = musicListMapper.musicListToApiResponse(musicList,memberPlayList);

        return  new ResponseEntity<>(
                new MultiResponseDto<>(responseMusic,musicPage), HttpStatus.OK);

    }


    @GetMapping(value = "/{member-id}", params = {"page"})
    public ResponseEntity<?> getPersonalPlayList(@PathVariable("member-id")@Positive long memberId,
                                                  @Valid @RequestParam(name = "page", defaultValue = "1") @Positive int page,
                                                 @AuthenticationName String email){
        Page<PlayList> playListPage = musicListService.findPersonalMusicListsPage(email,memberId,page);
        List<PlaylistDto.ApiResponse> responseMusic = musicListMapper.musicListToPlayListResponseList(playListPage.getContent());
        return new ResponseEntity<>(
                new MultiResponseDto<>(responseMusic,playListPage), HttpStatus.OK);

    }

    @GetMapping(value = "/{dogOrCats}/id/{memberId}",params = {"page"})
    public ResponseEntity<?> getPersonalPlayListByCategoryAndTags(@PathVariable(name = "dogOrCats") String dogOrCats,@PathVariable(name = "memberId") long memberId,
                                                        @RequestParam(name = "page", defaultValue = "1") int page,
                                                        @RequestParam(name = "tags",required = false)String tags,
                                                                  @AuthenticationName String email){

        Music.Category category = Music.Category.valueOf(dogOrCats.toUpperCase());
        Page<Music> musicPage = musicService.findCategoryAndTagsPageMusic(category,tags,page);
        List<Music> musicList = musicPage.getContent();
        List<PlayList> likeMusic = musicListService.findPersonalMusicLists(memberId);
        List<PlaylistDto.ApiResponse> apiResponse = musicListMapper.musicListToApiResponse(musicList,likeMusic);

        return new ResponseEntity<>(
                new MultiResponseDto<>(apiResponse,musicPage), HttpStatus.OK);

    }

    @DeleteMapping(value = "/{member-id}", name = "music_name")
    public ResponseEntity<?> deletePersonalPlayList(@PathVariable("member-id")@Positive long id,
                                                  @Valid @RequestBody MusicDto.DeleteRequest postRequest,
                                                    @AuthenticationName String email){
        Member member = memberService.findMember(id);
        Music music = musicService.findMusic(postRequest.getMusicId());
        musicListService.deletePlayList(member, music);
        return ResponseEntity.noContent().build();
    }


}
