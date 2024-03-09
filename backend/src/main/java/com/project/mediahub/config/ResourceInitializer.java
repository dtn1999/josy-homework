package com.project.mediahub.config;

import com.project.mediahub.repository.UserRepository;
import com.project.mediahub.service.note.FilesStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResourceInitializer implements CommandLineRunner {
    private final FilesStorageService storageService;
    private final UserRepository userRepository;
    private final DummyDataProperties dummyDataProperties;

    @Override
    public void run(String... args) throws Exception {
        storageService.init();
        userRepository.saveAll(dummyDataProperties.getUsers());
    }


}
