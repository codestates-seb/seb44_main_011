package com.seb44main011.petplaylist.global.utils;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class UriCreator {
    public static URI createUri(String defaultUrl, long resourceId) {
        return UriComponentsBuilder
                .newInstance()
                .path(defaultUrl + "/{resource-id}")
                .buildAndExpand(resourceId)
                .toUri();
    }

    public static URI createUri(String defaultUrl) {
        return UriComponentsBuilder
                .newInstance()
                .path(defaultUrl)
                .build()
                .toUri();
    }
}
