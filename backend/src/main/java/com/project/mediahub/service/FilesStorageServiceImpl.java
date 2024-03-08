package com.project.mediahub.service;

import com.project.mediahub.model.FileProcessingException;
import com.project.mediahub.model.entity.Upload;
import com.project.mediahub.repository.UploadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Slf4j
public class FilesStorageServiceImpl implements FilesStorageService {
    private final Path root = Paths.get("uploads");

    @Override
    public Upload save(MultipartFile file) {
        try {
            String extension = UploadUtils.extractFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
            String filename = String.format("%s.%s", UUID.randomUUID(), extension);
            Files.copy(file.getInputStream(), this.root.resolve(Objects.requireNonNull(file.getOriginalFilename())));
            return Upload.builder()
                    .filename(filename)
                    .build();
        } catch (FileAlreadyExistsException e) {
            log.error("A file of that name already exists!", e);
            throw new FileProcessingException("A file of that name already exists.");
        } catch (Exception e) {
            log.error("Could not save file!", e);
            throw new FileProcessingException(e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                log.error("Could not read the file!");
                throw new FileProcessingException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            log.error("Error: " + e.getMessage());
            throw new FileProcessingException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteFilename(String filename) {
        try {
            Files.delete(root.resolve(filename));
        } catch (Exception e) {
            log.error("Could not delete file!", e);
            throw new FileProcessingException("Could not delete file!");
        }
    }
}
