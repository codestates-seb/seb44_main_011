package com.seb44main011.petplaylist.domain.music.service;

import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.repository.MusicRepository;
import com.seb44main011.petplaylist.global.error.BusinessLogicException;
import com.seb44main011.petplaylist.global.error.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MusicService {
    private final MusicRepository repository;

    public Music findMusic(String musicTitle){
        return findVerifiedMusic(musicTitle);
    }


    public Music findMusic(long musicId){
        return findVerifiedMusic(musicId);
    }


    private Music findVerifiedMusic(String musicTitle) {
        return repository.findByTitle(musicTitle)
                .orElseThrow(()->
                        new BusinessLogicException(ExceptionCode.MUSIC_NOT_FOUND));
    }

    private Music findVerifiedMusic(long musicId) {
        return repository.findById(musicId)
                .orElseThrow(()->
                        new BusinessLogicException(ExceptionCode.MUSIC_NOT_FOUND));
    }
}
