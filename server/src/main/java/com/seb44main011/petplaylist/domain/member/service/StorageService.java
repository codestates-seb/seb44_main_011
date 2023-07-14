package com.seb44main011.petplaylist.domain.member.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void store(MultipartFile file);
}
