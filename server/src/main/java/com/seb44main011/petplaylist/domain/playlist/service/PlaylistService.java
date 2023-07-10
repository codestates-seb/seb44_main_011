package com.seb44main011.petplaylist.domain.playlist.service;

import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.service.MusicService;
import com.seb44main011.petplaylist.domain.playlist.entity.entityTable.MusicList;
import com.seb44main011.petplaylist.domain.playlist.entity.entityTable.PersonalPlayList;
import com.seb44main011.petplaylist.domain.playlist.repository.PlaylistRepository;
import com.seb44main011.petplaylist.global.error.BusinessLogicException;
import com.seb44main011.petplaylist.global.error.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PlaylistService {
    private final MusicService musicService;
    private final MusicListService musicListService;
    private final PlaylistRepository repository;

    public PersonalPlayList findPersonalPlayList(long memberId) {
       return repository.findByMember_MemberId(memberId)
               .orElseThrow(
                       ()->new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND)
               );

    }


//    private void updatePersonalPlayList(PersonalPlayList playList, Music music) {
//        MusicList musicList = musicListService.createMusicList(playList,music);
//        playList.insertMusicList(musicList);
//        repository.save(playList);
//    }

}
