package com.project.mediahub.service.security;

import com.project.mediahub.model.UserAlreadyExistsException;
import com.project.mediahub.model.entity.User;
import com.project.mediahub.model.payload.RegistrationRequest;
import com.project.mediahub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

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

}

