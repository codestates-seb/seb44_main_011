package com.seb44main011.petplaylist.domain.music.service.storageService;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface StorageService {
    Map<String,String> saveUploadFile(List<MultipartFile> multipartHttpServletRequest) throws IOException;
}
