package com.seb44main011.petplaylist.domain.playlist.repository;

import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.playlist.entity.PersonalPlayList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<PersonalPlayList, Long> {
}
