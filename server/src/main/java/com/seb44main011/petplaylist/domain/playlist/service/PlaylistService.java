package com.seb44main011.petplaylist.domain.playlist.service;

import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.member.repository.MemberRepository;
import com.seb44main011.petplaylist.domain.member.service.MemberService;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.service.MusicService;
import com.seb44main011.petplaylist.domain.playlist.entity.MusicList;
import com.seb44main011.petplaylist.domain.playlist.entity.PersonalPlayList;
import com.seb44main011.petplaylist.domain.playlist.mapper.PlaylistMapper;
import com.seb44main011.petplaylist.domain.playlist.repository.PlaylistRepository;
import com.seb44main011.petplaylist.global.error.BusinessLogicException;
import com.seb44main011.petplaylist.global.error.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaylistService {
    private final MusicService musicService;
    private final MemberService memberService;
    private final PlaylistRepository repository;
    private final PlaylistMapper mapper;

    public void createPersonalMusicList(long memberId, Music music){
        PersonalPlayList playList = findPlayList(memberId);
        Music findMusic = musicService.findMusic(music.getMusicId());
        updatePersonalPlayList(playList,findMusic);
    }

    private PersonalPlayList findPlayList(long memberId) {
       return repository.findByMember_MemberId(memberId)
               .orElseThrow(
                       ()->new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND)
               );

    }

    private void updatePersonalPlayList(PersonalPlayList playList, Music Music) {
        MusicList musicList = mapper.MemberAndMusicToMusicList(playList,Music);
        playList.insertMusicList(musicList);
        repository.save(playList);
    }

}
