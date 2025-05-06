package com.ecommerce.backend.config;

import com.ecommerce.backend.filter.JwtAuthFilter;
import com.ecommerce.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final UserRepository userRepository;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Register/Login allowed for all
                        .requestMatchers("/api/products/public/**").permitAll() // Public endpoint
                        .requestMatchers("/api/products/admin/**").hasRole("ADMIN") // Only ADMIN
                        .requestMatchers("/api/products/user/**").hasAnyRole("USER", "ADMIN") // USER or ADMIN
                        .anyRequest().authenticated() // All others require authentication
                )
                .addFilterBefore(new JwtAuthFilter(userRepository, jwtSecret),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
