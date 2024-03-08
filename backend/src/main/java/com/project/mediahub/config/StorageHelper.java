package com.project.mediahub.config;

import com.project.mediahub.service.note.FilesStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StorageHelper implements CommandLineRunner {
    private final FilesStorageService storageService;

    @Override
    public void run(String... args) throws Exception {
        storageService.init();
    }

}
