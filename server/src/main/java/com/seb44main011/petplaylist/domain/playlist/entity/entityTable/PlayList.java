package com.seb44main011.petplaylist.domain.playlist.entity.entityTable;

import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.playlist.entity.compositeKey.PlayListId;
import com.seb44main011.petplaylist.global.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@IdClass(PlayListId.class)
public class PlayList extends BaseTimeEntity {
    @Builder
    public PlayList(Member member, Music music) {
        this.member = member;
        this.music = music;
    }
    @Id
    @ManyToOne
    @JoinColumn(name="MEMBER_ID")
    private Member member;
    @Id
    @ManyToOne
    @JoinColumn(name="MUSIC_ID")
    private Music music;

    @Column(nullable = false)
    private boolean liked =true;



    public void updateMusic(Music music) {
        this.music = music;
    }

}
