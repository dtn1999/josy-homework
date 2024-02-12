package com.project.mediahub.config;

import com.project.mediahub.repository.UserRepository;
import com.project.mediahub.service.AuthenticationService;
import com.project.mediahub.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationService authenticationService(final UserService userService) {
        return new AuthenticationService(userService);
    }

    @Bean
    public UserService userService(final PasswordEncoder passwordEncoder, final UserRepository userRepository) {
        return new UserService(passwordEncoder, userRepository);
    }

}
