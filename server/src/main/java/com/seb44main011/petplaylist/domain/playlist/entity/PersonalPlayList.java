package com.seb44main011.petplaylist.domain.playlist.entity;

import com.seb44main011.petplaylist.domain.music.entity.Music;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class PersonalPlayList {
    @Id
    private Long memberId;
    @ManyToOne
    @JoinColumn(name = "MUSIC_ID")
    private Music music;
}
