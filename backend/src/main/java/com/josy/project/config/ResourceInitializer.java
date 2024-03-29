package com.josy.project.config;

import com.josy.project.model.entity.User;
import com.josy.project.repository.UserRepository;
import com.josy.project.service.note.FilesStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ResourceInitializer implements CommandLineRunner {
    private final FilesStorageService storageService;
    private final UserRepository userRepository;
    private final DummyDataProperties dummyDataProperties;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        storageService.init();
        List<User> users = dummyDataProperties.getUsers();
        users.forEach(user -> {
            if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setEnabled(true);
                userRepository.save(user);
            }
        });
    }


}
