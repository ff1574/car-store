package com.rit.carstore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

// This class is a configuration class for Spring Security.
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // This method configures the security filter chain.
    // It sets up CORS, disables CSRF (as this is an API), sets the session to be
    // stateless, and configures the authorization rules.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Enable CORS with proper configuration
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable()) // Disable CSRF for API usage
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Use
                                                                                                              // stateless
                                                                                                              // session;
                                                                                                              // this is
                                                                                                              // important
                                                                                                              // for
                                                                                                              // REST
                                                                                                              // APIs
                .authorizeHttpRequests(auth -> auth
                        // Allow all requests to "/auth/**" without authentication
                        .requestMatchers("/auth/**").permitAll()
                        // Allow all requests to "/api/**" without authentication
                        .requestMatchers("/api/**").permitAll()
                        // Require authentication for all other requests
                        // This is currently commented, it will later be used to require authentication
                        // after the application works properly without it
                        // .anyRequest().authenticated());
                        .anyRequest().permitAll());

        return http.build();
    }

    // This method configures CORS.
    // It allows requests from "http://localhost:3000", allows all HTTP methods,
    // allows all headers, and allows credentials.
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Allowed origin for CORS
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true); // Necessary for cookies, authorization headers etc.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // This method provides a BCryptPasswordEncoder bean.
    // This can be used wherever a password encoder is needed in the application.
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}