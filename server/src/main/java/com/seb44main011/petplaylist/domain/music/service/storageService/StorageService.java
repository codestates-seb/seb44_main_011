package com.seb44main011.petplaylist.domain.music.service.storageService;

import com.seb44main011.petplaylist.domain.music.entity.Music;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface StorageService<T,P> {
    void saveUploadFile(P fileTypeData,T entity) throws IOException;

    void convertFileStatus(T entity);

}
