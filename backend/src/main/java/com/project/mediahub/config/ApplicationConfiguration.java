package com.project.mediahub.config;

import com.project.mediahub.repository.*;
import com.project.mediahub.service.note.FilesStorageService;
import com.project.mediahub.service.note.FilesStorageServiceImpl;
import com.project.mediahub.service.note.NoteService;
import com.project.mediahub.service.security.AuthenticationService;
import com.project.mediahub.service.security.JwtTokenFilter;
import com.project.mediahub.service.security.TokenService;
import com.project.mediahub.service.security.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.project.mediahub.repository"
)
public class ApplicationConfiguration implements WebMvcConfigurer {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity,
            JwtTokenFilter jwtTokenFilter
    ) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {
                    cors.configurationSource(request -> {
                        var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
                        corsConfiguration.setAllowedOrigins(java.util.List.of("*"));
                        corsConfiguration.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE"));
                        corsConfiguration.setAllowedHeaders(java.util.List.of("*"));
                        return corsConfiguration;
                    });
                })
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers("/api/auth/register", "/api/auth/login", "/api/notes/files/**", "/uploads/**")
                                .permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter(
            UserService userService,
            TokenService tokenService) {
        return new JwtTokenFilter(userService, tokenService);
    }


    @Bean
    public DaoAuthenticationProvider authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationService authenticationService(
            final UserService userService,
            DaoAuthenticationProvider authenticationProvider) {
        return new AuthenticationService(userService, authenticationProvider);
    }

    @Bean
    public TokenService tokenService(BlackListedTokenRepository repository) {
        return new TokenService(repository);
    }


    @Bean
    public AuthenticationManagerBuilder authenticationManagerBuilder(
            AuthenticationManagerBuilder auth,
            DaoAuthenticationProvider provider) {
        auth.authenticationProvider(provider);
        return auth;
    }

    @Bean
    public NoteService noteService(final NoteRepository noteRepository,
                                   final FilesStorageService storageService,
                                   final TagRepository tagRepository,
                                   final UploadRepository uploadRepository) {
        return new NoteService(noteRepository, tagRepository, uploadRepository, storageService);
    }


    @Bean
    public FilesStorageService filesStorageService() {
        return new FilesStorageServiceImpl();
    }

    @Bean
    public UserService userService(final PasswordEncoder passwordEncoder, final UserRepository userRepository) {
        return new UserService(passwordEncoder, userRepository);
    }

}
