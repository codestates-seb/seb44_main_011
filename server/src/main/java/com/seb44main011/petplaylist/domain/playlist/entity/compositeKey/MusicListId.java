package com.seb44main011.petplaylist.domain.playlist.entity.compositeKey;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MusicListId implements Serializable {
    private Long personalPlayList;
    private Long music;
}
