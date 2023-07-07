package com.seb44main011.petplaylist.domain.playlist.service;

import com.seb44main011.petplaylist.domain.member.service.MemberService;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.service.MusicService;
import com.seb44main011.petplaylist.domain.playlist.entity.entityTable.MusicList;
import com.seb44main011.petplaylist.domain.playlist.entity.entityTable.PersonalPlayList;
import com.seb44main011.petplaylist.domain.playlist.mapper.PlaylistMapper;
import com.seb44main011.petplaylist.domain.playlist.repository.PlaylistRepository;
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
public class PlaylistService {
    private final MusicService musicService;
    private final MusicListService musicListService;
    private final PlaylistRepository repository;


    public void createPersonalMusicList(long memberId, long musicId){
        PersonalPlayList playList = findPlayList(memberId);
        Music findMusic = findMusicById(musicId);
        updatePersonalPlayList(playList,findMusic);
    }

    public void deletePersonalMusicList(long memberId, long musicId){
        PersonalPlayList playList = findPlayList(memberId);
        Music findMusic = findMusicById(musicId);
        deleteMusicList(playList,findMusic);

    }

    private void deleteMusicList(PersonalPlayList playList, Music music) {
        MusicList musicList = musicListService.findMusicListMusic(playList,music);
        log.info("Music List.Music id: {}",musicList.getMusic().getMusicId());
        playList.deleteMusicList(musicList);
        repository.save(playList);
    }

    private PersonalPlayList findPlayList(long memberId) {
       return repository.findByMember_MemberId(memberId)
               .orElseThrow(
                       ()->new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND)
               );

    }
    private Music findMusicById(long musicId) {
        return musicService.findMusic(musicId);
    }


    private void updatePersonalPlayList(PersonalPlayList playList, Music music) {
        MusicList musicList = musicListService.CreateMusicList(playList,music);
        playList.insertMusicList(musicList);
        repository.save(playList);
    }

}
package com.seb44main011.petplaylist.domain.playlist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaylistService {
}
