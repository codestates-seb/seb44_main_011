package com.seb44main011.petplaylist.domain.music.entity;

import com.seb44main011.petplaylist.domain.playlist.entity.PersonalPlayList;
import com.seb44main011.petplaylist.global.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Music extends BaseTimeEntity {
    @Builder
    public Music(long musicId, String title, String music_url, String image_url, List<PersonalPlayList> personalPlayLists, Category category, Tags tags) {
        this.musicId = musicId;
        this.title = title;
        this.music_url = music_url;
        this.image_url = image_url;
        this.personalPlayLists = personalPlayLists;
        this.category = category;
        this.tags = tags;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long musicId;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String music_url;
    @Column(nullable = false)
    private String image_url;
    @OneToMany(mappedBy="music")
    private List<PersonalPlayList> personalPlayLists;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Enumerated(value =  EnumType.STRING)
    @Column(nullable = false)
    private Tags tags;

    public enum Category{
        DOGS("강아지"),
        CATS("고양이");

        @Getter
        private final String category;

        Category(String category) {
            this.category = category;
        }
    }

    public enum Tags{
        RELAXING("편안한"),
        UPBEAT("흥겨운"),
        HAPPY("행복한"),
        CALM("차분한"),
        SERENE("고요한");

        @Getter
        private final String tags;

        Tags(String tags) {
            this.tags = tags;
        }
    }




}
