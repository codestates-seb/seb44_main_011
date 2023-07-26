package com.seb44main011.petplaylist.domain.playlist.controller;

import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.member.service.MemberService;
import com.seb44main011.petplaylist.domain.music.dto.MusicDto;
import com.seb44main011.petplaylist.domain.music.entity.Music;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.net.URI;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/playlist")
@Slf4j
@RequiredArgsConstructor
public class ApiPlaylistController {

    private final MusicListMapper mapper;

    private final MemberService memberService;
    private final MusicListService musicListService;
    private final MusicService musicService;

    @PostMapping(value = "/{member-id}")
    public ResponseEntity<?> postPersonalPlayList(@PathVariable("member-id")@Positive long memberId,
                                                  @Valid @RequestBody MusicDto.PostRequest postRequest,
                                                  @AuthenticationName @NotNull String email){
        musicListService.updatePlayList(postRequest,email,memberId);
        URI location = UriCreator.createUri("/api/playlist");

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<?> getAllMusicListFromMember(@RequestParam(name = "member-id",required = false) @Positive int memberId,
                                                       @RequestParam(name = "page", defaultValue = "1") @Positive int page,
                                                       @RequestParam(name = "sort",required = false,defaultValue = "view")String sortValue,
                                                       @AuthenticationName  @NotNull String email) {
        List<PlayList> memberPlayList = musicListService.findPersonalMusicLists(email);
        Page<Music> musicPage= musicService.findMusicListAll(page,sortValue);
        List<Music> musicList = musicPage.getContent();
        List<PlaylistDto.ApiResponse> responseMusic = mapper.musicListToApiResponse(musicList,memberPlayList);

        return  new ResponseEntity<>(
                new MultiResponseDto<>(responseMusic,musicPage), HttpStatus.OK);

    }


    @GetMapping(value = "/{member-id}")
    public ResponseEntity<?> getPersonalPlayList(@PathVariable("member-id")@Positive long memberId,
                                                 @RequestParam(name = "page", defaultValue = "1") @Positive int page,
                                                 @AuthenticationName @NotNull String email){
        Page<PlayList> playListPage = musicListService.findPersonalMusicListsPage(email,memberId,page);
        List<PlaylistDto.ApiResponse> responseMusic = mapper.musicListToPlayListResponseList(playListPage.getContent());
        return new ResponseEntity<>(
                new MultiResponseDto<>(responseMusic,playListPage), HttpStatus.OK);

    }

    @GetMapping(value = "/{dogOrCats}/id/{memberId}")
    public ResponseEntity<?> getPersonalPlayListByCategoryAndTags(@PathVariable(name = "dogOrCats") @NotNull String dogOrCats,
                                                                  @PathVariable(name = "memberId") @Positive long memberId,
                                                                  @RequestParam(name = "page", defaultValue = "1") int page,
                                                                  @RequestParam(name = "tags",required = false)String tags,
                                                                  @RequestParam(name = "sort",required = false,defaultValue = "view")String sortValue,
                                                                  @AuthenticationName  @NotNull String email){

        Music.Category category = Music.Category.valueOf(dogOrCats.toUpperCase());
        Page<Music> musicPage = musicService.findCategoryAndTagsPageMusic(category,tags,page,sortValue);
        List<Music> musicList = musicPage.getContent();
        List<PlayList> likeMusic = musicListService.findPersonalMusicLists(memberId);
        List<PlaylistDto.ApiResponse> apiResponse = mapper.musicListToApiResponse(musicList,likeMusic);

        return new ResponseEntity<>(
                new MultiResponseDto<>(apiResponse,musicPage), HttpStatus.OK);

    }

    @DeleteMapping(value = "/{member-id}", name = "music_name")
    public ResponseEntity<?> deletePersonalPlayList(@PathVariable("member-id")@Positive long id,
                                                  @Valid @RequestBody MusicDto.DeleteRequest postRequest,
                                                    @AuthenticationName @NotNull  String email){
        Member member = memberService.findMember(id);
        Music music = musicService.findMusic(postRequest.getMusicId());
        musicListService.deletePlayList(member, music);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/search")
    public ResponseEntity<?> getSearchTitleMusicListFromMember(@RequestParam(value = "title") @Size(min = 2) String title,
                                                               @RequestParam(value = "page", defaultValue = "1") int page,
                                                               @RequestParam(value = "sort", required = false, defaultValue = "view") String sortValue,
                                                               @AuthenticationName String email){
        Page<Music> musicPage = musicService.findMusicListFromTitle(title,page,sortValue);
        List<Music> musicList = musicPage.getContent();
        List<PlayList> likeMusic = musicListService.findPersonalMusicLists(email);
        List<PlaylistDto.ApiResponse> apiResponse = mapper.musicListToApiResponse(musicList,likeMusic);

        return ResponseEntity.ok().body(
                new MultiResponseDto<>(apiResponse,musicPage)
        );
    }


}
