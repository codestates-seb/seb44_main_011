package com.seb44main011.petplaylist.domain.playlist.entity;

import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.global.common.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class PersonalPlayList extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personalPlayListId;

    @OneToOne(mappedBy="personalPlayList")
    private Member member;

    @OneToMany(mappedBy = "personalPlayList",cascade =CascadeType.ALL)
    private List<MusicList> musicLists;

    public void insertMusicList(MusicList musicList) {
        this.musicLists.add(musicList);
    }

    public void insertMember(Member member) {
        this.member = member;
    }
}
//@JoinColumn(name = "STAMP_ID")
