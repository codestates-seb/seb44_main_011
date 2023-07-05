package com.seb44main011.petplaylist.domain.playlist.entity;

import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class MusicList {
    @Builder
    public MusicList(PersonalPlayList personalPlayList, Music music) {
        this.personalPlayList = personalPlayList;
        this.music = music;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long MusicListId;

    @ManyToOne
    @JoinColumn(name="PERSONALPLAYLIST_ID")
    private PersonalPlayList personalPlayList;
    @ManyToOne
    @JoinColumn(name="MUSIC_ID")
    private Music music;

    @Column(nullable = false)
    private boolean liked;



    public void updateMusic(Music music) {
        this.music = music;
    }

    public void updatePersonalPlayList(PersonalPlayList personalPlayList) {
        this.personalPlayList = personalPlayList;
    }
}
