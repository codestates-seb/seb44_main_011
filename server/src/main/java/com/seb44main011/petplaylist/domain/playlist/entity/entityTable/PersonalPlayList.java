package com.seb44main011.petplaylist.domain.playlist.entity.entityTable;

import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.global.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


//@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class PersonalPlayList extends BaseTimeEntity {
    @Builder
    public PersonalPlayList(Member member) {
        this.member = member;
        this.musicLists =new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personalPlayListId;

    @OneToOne(mappedBy="personalPlayList")
    private Member member;

    @OneToMany(mappedBy = "personalPlayList",cascade =CascadeType.ALL)
    private List<MusicList> musicLists;

    public void insertMusicList(MusicList musicList) {
        if (musicLists.isEmpty()){
            musicLists = new ArrayList<>();
            musicLists.add(musicList);
        }
        this.musicLists.add(musicList);
    }
    public void deleteMusicList(MusicList musicList){
       if(this.musicLists.contains(musicList)) {
           musicLists.remove(musicList);
       }
       else {
           throw new RuntimeException("리스트 오류 입니다");
       }

    }

    public void insertMember(Member member) {
        this.member = member;
    }
}
//@JoinColumn(name = "STAMP_ID")
