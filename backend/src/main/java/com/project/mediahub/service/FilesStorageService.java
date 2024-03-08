package com.project.mediahub.service;

import com.project.mediahub.model.entity.Upload;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
    Upload save(MultipartFile file);
    Resource load(String filename);
    void deleteFilename(String filename);
}
