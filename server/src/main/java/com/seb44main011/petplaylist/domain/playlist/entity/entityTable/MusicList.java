package com.seb44main011.petplaylist.domain.playlist.entity.entityTable;

import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.playlist.entity.compositeKey.MusicListId;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@IdClass(MusicListId.class)
public class MusicList {
    @Builder
    public MusicList(PersonalPlayList personalPlayList, Music music) {
        this.personalPlayList = personalPlayList;
        this.music = music;
    }
    @Id
    @ManyToOne
    @JoinColumn(name="PERSONALPLAYLIST_ID")
    private PersonalPlayList personalPlayList;
    @Id
    @ManyToOne
    @JoinColumn(name="MUSIC_ID")
    private Music music;

    @Column(nullable = false)
    private boolean liked = true;



    public void updateMusic(Music music) {
        this.music = music;
    }

    public void updatePersonalPlayList(PersonalPlayList personalPlayList) {
        this.personalPlayList = personalPlayList;
    }
}
