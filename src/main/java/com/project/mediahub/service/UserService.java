package com.project.mediahub.service;

import com.project.mediahub.model.User;
import com.project.mediahub.model.RegistrationRequest;
import com.project.mediahub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class UserService implements UserDetailsPasswordService, UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository profileRepository;


    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public User register(final RegistrationRequest request) {
        return this.profileRepository.save(User.builder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .enabled(true)
                .build()
        );
    }

}

