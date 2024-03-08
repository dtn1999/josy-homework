package com.project.mediahub.service.note;

import com.project.mediahub.model.entity.Upload;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
    void init();
    Upload save(MultipartFile file);
    Resource load(String filename);
    void deleteFilename(String filename);
}
