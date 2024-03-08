package com.project.mediahub.config;

import com.project.mediahub.repository.UserRepository;
import com.project.mediahub.service.security.AuthenticationService;
import com.project.mediahub.service.security.JwtTokenFilter;
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
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers("/api/auth/**")
                                .permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter(UserService userService) {
        return new JwtTokenFilter(userService);
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
    public AuthenticationManagerBuilder authenticationManagerBuilder(
            AuthenticationManagerBuilder auth,
            DaoAuthenticationProvider provider) {
        auth.authenticationProvider(provider);
        return auth;
    }

    @Bean
    public UserService userService(final PasswordEncoder passwordEncoder, final UserRepository userRepository) {
        return new UserService(passwordEncoder, userRepository);
    }

}
