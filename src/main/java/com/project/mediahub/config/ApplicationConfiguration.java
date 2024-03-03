package com.project.mediahub.config;

import com.project.mediahub.repository.UserRepository;
import com.project.mediahub.service.AuthenticationService;
import com.project.mediahub.service.UserService;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.project.mediahub.repository"
)
public class ApplicationConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                .permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .permitAll()
                )
                .logout(LogoutConfigurer::permitAll)
                .build();
    }

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
