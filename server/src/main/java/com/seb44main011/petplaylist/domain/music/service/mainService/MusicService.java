package com.seb44main011.petplaylist.domain.music.service.mainService;

import com.seb44main011.petplaylist.domain.music.dto.MusicDto;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.mapper.MusicMapper;
import com.seb44main011.petplaylist.domain.music.repository.MusicRepository;
import com.seb44main011.petplaylist.domain.music.service.storageService.S3Service;
import com.seb44main011.petplaylist.domain.music.service.storageService.StorageService;
import com.seb44main011.petplaylist.global.error.BusinessLogicException;
import com.seb44main011.petplaylist.global.error.ExceptionCode;
import com.seb44main011.petplaylist.global.utils.PageNationCreator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MusicService  {
    private final MusicRepository repository;
    private final S3Service storageService;
    private final MusicMapper mapper;

    public void uploadMusic(List<MultipartFile> files, MusicDto.PostMusicFile postMusicFile) {

        verifiedMusic(postMusicFile.getTitle());
        Music insertMusic = saveUploadS3(files,mapper.musicToMusicPostDto(postMusicFile));
        saveMusic(insertMusic);
    }

    public Music serchMusic(String musicTitle){
        Music findMusic= findMusic(musicTitle);
        inViewMusic(findMusic);
        return repository.save(findMusic);
    }

    public Music serchMusic(long musicId){
        Music findMusic= findMusic(musicId);
        inViewMusic(findMusic);
        return repository.save(findMusic);
    }

    public void deleteMusicFile(long musicId) {
        Music findMusic = findMusic(musicId);
        convertMusicStatus(findMusic);

    }

    private void convertMusicStatus(Music music) {
        if (music.getStatus().equals(Music.Status.ACTIVE)) {
            Map<String,String> saveUploadFile = storageService.deactivateFile(List.of(music.getMusic_url(),music.getImage_url()));
            updateMusicToS3Data(music, saveUploadFile);
            music.convertStatus(Music.Status.INACTIVE);
        }else {
            throw new BusinessLogicException(ExceptionCode.HIDDEN_MUSIC);
        }
        repository.save(music);
    }

    private static void inViewMusic(Music findMusic) {
        log.info("재생 요청 시간 : {}", DateTime.now());
        findMusic.incrementView();
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
    @Transactional(readOnly = true)
    public Music findMusic(String musicTitle){
        return findMusicByTitle(musicTitle);
    }

    @Transactional(readOnly = true)
    public Music findMusic(long musicId){
        return findMusicByTitle(musicId);
    }



    private void verifiedMusic(String title) {
        Optional<Music> optionalMusic = repository.findByTitle(title);
        if (optionalMusic.isPresent()){
            throw new BusinessLogicException(ExceptionCode.MUSIC_EXISTS);
        }
    }



    private Music findMusicByTitle(String musicTitle) {
        Music music = repository.findByTitle(musicTitle)
                .orElseThrow(()->
                        new BusinessLogicException(ExceptionCode.MUSIC_NOT_FOUND));
        if (music.getStatus().equals(Music.Status.INACTIVE)){
            throw new BusinessLogicException(ExceptionCode.HIDDEN_MUSIC);
        }
        return music;
    }

    private Music findMusicByTitle(long musicId) {
        return repository.findById(musicId)
                .orElseThrow(()->
                        new BusinessLogicException(ExceptionCode.MUSIC_NOT_FOUND));
    }

    private void saveMusic(Music music){
        repository.save(music);
    }


    @SneakyThrows
    private Music saveUploadS3(List<MultipartFile> files, Music music) {
        Map<String,String> saveUploadFile = storageService.saveUploadFile(files);
        updateMusicToS3Data(music, saveUploadFile);
        return music;

    }

    private static void updateMusicToS3Data(Music music, Map<String, String> saveUploadFile) {
        music.insertMusic_url(saveUploadFile.get("music_url"));
        music.insertImage_url(saveUploadFile.get("image_url"));
        if (saveUploadFile.containsKey("playtime")) {
            music.insertPlaytime(saveUploadFile.get("playtime"));
        }
    }

    private static Pageable getPageInfo(int page) {
        return PageNationCreator.getPageOfDesc(page, PageNationCreator.ORIGIN_PAGE_SIZE_OF_SIX, "view");
    }



}
