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
    Page<Music> findAllByStatusAndTitleContaining(Music.Status status, String title, Pageable pageable);
    Page<Music> findAllByStatus(Music.Status status,Pageable pageable);
    Page<Music> findByCategoryAndStatus(Music.Category category, Music.Status status, Pageable pageable);
    Page<Music> findByCategoryAndTagsAndStatus(Music.Category category, Music.Tags tags,Music.Status status,Pageable pageable);
}
