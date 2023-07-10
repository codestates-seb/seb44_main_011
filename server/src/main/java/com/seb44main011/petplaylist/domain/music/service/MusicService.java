package com.seb44main011.petplaylist.domain.music.service;

import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.repository.MusicRepository;
import com.seb44main011.petplaylist.global.error.BusinessLogicException;
import com.seb44main011.petplaylist.global.error.ExceptionCode;
import com.seb44main011.petplaylist.global.utils.PageNationCreator;
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
@Slf4j
public class MusicService {
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

    @Transactional(readOnly = true)
    public Page<Music> findMusicListAll(int page){
        Pageable pageable = getPageInfo(page);
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Music> findCategoryAndTagsPageMusic(Music.Category category, String tags, int page) {
        Pageable pageable = getPageInfo(page);
        if (tags == null){
            return repository.findByCategory(category,pageable);
        }
        else {
            Music.Tags valueOfTags =Music.Tags.valueOf(tags.toUpperCase());
            return repository.findByCategoryAndTags(category,valueOfTags,pageable);
        }
    }

    private static Pageable getPageInfo(int page) {
        return PageNationCreator.getPageOfDesc(page, PageNationCreator.ORIGIN_PAGE_SIZE_OF_SIX, "view");
    }
}
