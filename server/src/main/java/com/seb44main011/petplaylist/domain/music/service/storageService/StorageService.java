package com.seb44main011.petplaylist.domain.music.service.storageService;

import java.io.IOException;


public interface StorageService<T,P> {
    void saveUploadFile(P fileTypeData,T entity) throws IOException;

    void convertFileStatus(T entity);

}
