package com.seb44main011.petplaylist.domain.music.repository;

import com.seb44main011.petplaylist.domain.music.entity.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface MusicRepository extends JpaRepository<Music, Long> {
    Optional<Music> findByTitle(String musicTitle);
    Page<Music> findByCategory(Music.Category category, Pageable pageable);
    Page<Music> findByCategoryAndTags(Music.Category category, Music.Tags tags,Pageable pageable);
}
