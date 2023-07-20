package com.seb44main011.petplaylist.domain.playlist.repository;

import com.seb44main011.petplaylist.domain.playlist.entity.entityTable.PlayList;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface MusicListRepository extends JpaRepository<PlayList, Long> {
    Optional<PlayList> findByMusic_MusicIdAndMember_MemberId(long musicId, long memberId);
    List<PlayList> findAllByMember_MemberId(long memberId);
    List<PlayList> findAllByMember_Email(String email);
}
