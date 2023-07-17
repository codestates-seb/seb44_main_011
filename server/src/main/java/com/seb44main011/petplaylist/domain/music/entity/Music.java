package com.seb44main011.petplaylist.domain.music.entity;

import com.seb44main011.petplaylist.domain.playlist.entity.entityTable.PlayList;

import com.seb44main011.petplaylist.global.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity(name="MUSIC")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Music extends BaseTimeEntity {
    @Builder
    public Music(long musicId, String title, String music_url, String image_url, String playtime, long view, Category category, Tags tags, List<PlayList> personalPlayLists) {
        this.musicId = musicId;
        this.title = title;
        this.music_url = music_url;
        this.image_url = image_url;
        this.playtime = playtime;
        this.view = view;
        this.category = category;
        this.tags = tags;
        this.personalPlayLists = personalPlayLists;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long musicId;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String music_url;
    @Column(nullable = false)
    private String image_url;
    @Column(nullable = false)
    private String playtime;

    @Column
    private long view;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Enumerated(value =  EnumType.STRING)
    @Column(nullable = false)
    private Tags tags;

    @Enumerated(value =  EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ACTIVE;

    @OneToMany(mappedBy = "music",cascade = CascadeType.ALL)
    private List<PlayList> personalPlayLists;

    public enum Category{
        DOGS("dogs"),
        CATS("cats");

        @Getter
        private final String category;

        Category(String category) {
            this.category = category;
        }
    }
    public enum Status{
        ACTIVE("active"),
        INACTIVE("inactive");

        @Getter
        private final String status;

        Status(String status) {
            this.status = status;
        }
    }

    public enum Tags{
        RELAXING("relaxing"),
        UPBEAT("upbeat"),
        HAPPY("happy"),
        CALM("calm"),
        SERENE("serene");

        @Getter
        private final String tags;

        Tags(String tags) {
            this.tags = tags;
        }
    }
    public void incrementView() {
        this.view++;
    }

    public void insertMusic_url(String music_url) {
        this.music_url = music_url;
    }

    public void insertImage_url(String image_url) {
        this.image_url = image_url;
    }
    public void insertPlaytime(String playtime) {
        this.playtime = playtime;
    }

    public void convertStatus(Status status) {
        this.status = status;
    }
}
