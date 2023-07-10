package com.seb44main011.petplaylist.domain.music.service;

import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.repository.MusicRepository;
import com.seb44main011.petplaylist.global.error.BusinessLogicException;
import com.seb44main011.petplaylist.global.error.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MusicService {
    private final int PAGESIZE = 6;
    private final MusicRepository repository;
    public Music serchMusic(String musicTitle){
        Music findMusic= findVerifiedMusic(musicTitle);
        findMusic.incrementView();
        return repository.save(findMusic);
    }
    public Music serchMusic(long musicId){
        Music findMusic= findVerifiedMusic(musicId);
        findMusic.incrementView();
        return repository.save(findMusic);
    }

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

    public Page<Music> findCategoryAndTagsPageMusic(Music.Category category, String tags, int page) {
        Pageable pageable = getPageOfViewDesc(page);
        if (tags == null){
            return repository.findByCategory(category,pageable);
        }
        else {
            Music.Tags valueOfTags =Music.Tags.valueOf(tags.toUpperCase());
            return repository.findByCategoryAndTags(category,valueOfTags,pageable);
        }


    }


    private Pageable getPageOfViewDesc(int page) {
        return PageRequest.of(page-1, PAGESIZE, Sort.by("view").descending());
    }
}
