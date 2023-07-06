package com.seb44main011.petplaylist.domain.playlist.repository;

import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.playlist.entity.entityTable.MusicList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MusicListRepository extends JpaRepository<MusicList, Long> {
    Optional<MusicList> findByMusic_MusicIdAndPersonalPlayList_PersonalPlayListId(long musicId, long playListId);
}
