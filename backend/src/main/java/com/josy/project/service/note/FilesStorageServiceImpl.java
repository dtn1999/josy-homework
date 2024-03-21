package com.josy.project.service.note;

import com.josy.project.model.FileProcessingException;
import com.josy.project.model.entity.Upload;
import com.josy.project.config.EnvironmentUtil;
import com.josy.project.util.UploadUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class FilesStorageServiceImpl implements FilesStorageService {
    private final Path root = Paths.get("data/images/uploads");
    private final EnvironmentUtil environmentUtil;

    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new FileProcessingException("Could not initialize folder for upload!");
        }
    }

    @Override
    public Upload save(MultipartFile file) {
        try {
            String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
            String extension = UploadUtils.extractFileExtension(originalFilename);
            String filename = "%s.%s".formatted(UUID.randomUUID(), extension);
            Files.copy(file.getInputStream(), this.root.resolve(Objects.requireNonNull(filename)));
            String imageUrl = "%s/uploads/%s".formatted(environmentUtil.getServerUrl(), filename);
            return Upload.builder()
                    .filename(filename)
                    .imageUrl(imageUrl)
                    .build();
        } catch (FileAlreadyExistsException e) {
            log.error("A file of that name already exists!", e);
            throw new FileProcessingException("A file of that name already exists.", e);
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
