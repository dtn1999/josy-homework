package com.josy.project.service.security;

import com.josy.project.model.entity.User;
import com.josy.project.model.payload.RegistrationRequest;
import com.josy.project.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    UserService userService;

    RegistrationRequest request = RegistrationRequest.builder()
            .firstName("John")
            .lastName("Doe")
            .email("test@email.com")
            .password("password")
            .build();

    @BeforeEach
    void setUp() {
        userService = new UserService(passwordEncoder, userRepository);
        this.userRepository.deleteAll();
    }

    @Test
    void test_register() {
        // given
        // when
        userService.register(request);

        // then
        userRepository.findByEmail(request.getEmail())
                .ifPresentOrElse(
                        user -> {
                            assertThat(request.getFirstName()).isEqualTo(user.getFirstName());
                            assertThat(request.getLastName()).isEqualTo(user.getLastName());
                            assertThat(passwordEncoder.matches(request.getPassword(), user.getPassword()))
                                    .isTrue();
                        },
                        () -> fail("User not found"));

    }

    @Test
    void test_login() {
        // given
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .build();
        this.userRepository.save(user);
        // when
        UserDetails userDetails = userService.loadUserByUsername(request.getEmail());

        // then
        assertThat(userDetails.getUsername()).isEqualTo(request.getEmail());
        assertThat(userDetails.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    void test_update_password() {
        // given
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .build();
        this.userRepository.save(user);
        // when
        UserDetails userDetails = userService.loadUserByUsername(request.getEmail());
        UserDetails updatedUserDetails = userService.updatePassword(userDetails, "newPassword");

        // then
        assertThat(updatedUserDetails.getUsername()).isEqualTo(request.getEmail());
        assertThat(passwordEncoder.matches("newPassword", updatedUserDetails.getPassword()))
                .isTrue();
    }

    @Test
    void test_delete() {
        // given
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .build();
        this.userRepository.save(user);
        // when
        UserDetails userDetails = userService.loadUserByUsername(request.getEmail());
        userService.delete(userDetails);

        // then
        assertThat(userRepository.findByEmail(request.getEmail())).isEmpty();
    }

}