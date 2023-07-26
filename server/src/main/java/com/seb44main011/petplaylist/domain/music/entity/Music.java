package com.seb44main011.petplaylist.domain.music.entity;

import com.seb44main011.petplaylist.domain.playlist.entity.entityTable.PlayList;

import com.seb44main011.petplaylist.global.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="MUSIC")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Music extends BaseTimeEntity {
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
    @Builder.Default
    private Status status = Status.ACTIVE;

    @Builder.Default
    @OneToMany(mappedBy = "music",cascade = CascadeType.ALL)
    private List<PlayList> personalPlayLists = new ArrayList<>();

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
