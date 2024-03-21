package com.josy.project.service.security;

import com.josy.project.repository.UserRepository;
import com.josy.project.model.UserAlreadyExistsException;
import com.josy.project.model.entity.User;
import com.josy.project.model.payload.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsPasswordService, UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository profileRepository;


    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return this.profileRepository.findByEmail(user.getUsername())
                .map(profile -> {
                    profile.setPassword(passwordEncoder.encode(newPassword));
                    return this.profileRepository.save(profile);
                })
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.profileRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void register(final RegistrationRequest request) {
        // check if user already exists
        Optional<User> potentialExistingUser = this.profileRepository.findByEmail(request.getEmail());
        if (potentialExistingUser.isPresent()) {
            throw new UserAlreadyExistsException(
                    String.format("User with email %s already exists", request.getEmail())
            );
        }
        this.profileRepository.save(User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .build()
        );
    }

    public void delete(UserDetails userDetails) {
        User user = this.profileRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        this.profileRepository.delete(user);
    }
}

