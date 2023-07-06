package com.seb44main011.petplaylist.domain.playlist.service;

import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.service.MusicService;
import com.seb44main011.petplaylist.domain.playlist.entity.entityTable.MusicList;
import com.seb44main011.petplaylist.domain.playlist.entity.entityTable.PersonalPlayList;
import com.seb44main011.petplaylist.domain.playlist.mapper.MusicListMapper;
import com.seb44main011.petplaylist.domain.playlist.repository.MusicListRepository;
import com.seb44main011.petplaylist.global.error.BusinessLogicException;
import com.seb44main011.petplaylist.global.error.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MusicListService {
    private final MusicListRepository repository;
    public final MusicListMapper mapper;


    public MusicList createMusicList(PersonalPlayList playList, Music music){
        return mapper.MemberAndMusicToMusicList(playList,music);
    }
    public MusicList findMusicListMusic(PersonalPlayList playList, Music music){
        log.info("Music Id:{}", music.getMusicId());
        return getMusicList(music.getMusicId(), playList.getPersonalPlayListId());
    }

    public void deleteMusicList(long memberId, long musicId){
        MusicList getMusicList = getMusicList(musicId,memberId);
        repository.delete(getMusicList);
    }

    private MusicList getMusicList(long musicId,long memberId) {
        return repository.findByMusic_MusicIdAndPersonalPlayList_PersonalPlayListId(musicId, memberId)
                .orElseThrow(
                        () -> new BusinessLogicException(ExceptionCode.MUSIC_NOT_FOUND)
                );
    }



}
