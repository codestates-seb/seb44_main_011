package com.seb44main011.petplaylist.domain.playlist.service;

import com.seb44main011.petplaylist.domain.music.entity.Music;
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

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MusicListService {
    private final MusicListRepository repository;
    public final MusicListMapper mapper;

    public void addMusicList(MusicList musicList){
        verifyExistsMusicList(musicList);
        repository.save(musicList);
    }

    public MusicList createMusicList(PersonalPlayList playList, Music music){
        return mapper.memberAndMusicToMusicList(playList,music);
    }

    public void deleteMusicList(PersonalPlayList playList, Music music){
        MusicList getMusicList = getMusicList(music.getMusicId(),playList.getPersonalPlayListId());
        repository.delete(getMusicList);
    }

    private void verifyExistsMusicList(MusicList musicList) {
        Optional<MusicList> optionalMusicList = repository.findByMusic_MusicIdAndPersonalPlayList_PersonalPlayListId(
                musicList.getMusic().getMusicId(),musicList.getPersonalPlayList().getPersonalPlayListId());
        if (optionalMusicList.isPresent()){
            throw new BusinessLogicException(ExceptionCode.LIKED_MUSIC_EXISTS);
        }
    }

    private MusicList getMusicList(long musicId,long memberId) {
        return repository.findByMusic_MusicIdAndPersonalPlayList_PersonalPlayListId(musicId, memberId)
                .orElseThrow(
                        () -> new BusinessLogicException(ExceptionCode.MUSIC_NOT_FOUND)
                );
    }



}
