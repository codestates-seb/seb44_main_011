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

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MusicListService {
    private final MusicListRepository repository;
    public final MusicListMapper mapper;


    public MusicList CreateMusicList(PersonalPlayList playList, Music music){
        return mapper.MemberAndMusicToMusicList(playList,music);
    }

//    public List<Music> findMusicListMusics(PersonalPlayList playList, Music music){
//
//    }
    public MusicList findMusicListMusic(PersonalPlayList playList, Music music){
        log.info("Music Id:{}", music.getMusicId());
        return repository.findByMusic_MusicIdAndPersonalPlayList_PersonalPlayListId(music.getMusicId(),playList.getPersonalPlayListId())
                .orElseThrow(
                        () -> new BusinessLogicException(ExceptionCode.MUSIC_NOT_FOUND)
                );
    }

}
