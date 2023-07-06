package com.seb44main011.petplaylist.domain.music.repository;

import com.seb44main011.petplaylist.domain.music.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MusicRepository extends JpaRepository<Music, Long> {
    Optional<Music> findByTitle(String musicTitle);
}
