package com.rit.carstore.Controllers;

import java.util.Map;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

// This is a REST controller for managing login in the system.
@RestController
@RequestMapping("/api/login")
public class LoginController {
    // RestTemplate is a synchronous HTTP client that we use to make HTTP requests.
    private final RestTemplate restTemplate;

    // Dependency injection of the RestTemplateBuilder which is used to create
    // RestTemplate instances.
    public LoginController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    // Endpoint for logging in a user.
    @PostMapping
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        // Check if email and password are provided.
        if (email == null || password == null) {
            return ResponseEntity.badRequest().body("Email and password must be provided");
        }

        // Create request entity
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(credentials);

        // Check if the user is a customer.
        try {
            restTemplate.exchange("http://localhost:8080/api/customer/check-password", HttpMethod.POST, requestEntity,
                    Void.class);
            return ResponseEntity.ok("ROLE_CUSTOMER");
        } catch (HttpClientErrorException.Unauthorized e) {
            // If the user is not a customer, ignore and try the next role.
        }

        // Check if the user is an administrator.
        try {
            restTemplate.exchange("http://localhost:8080/api/administrator/check-password", HttpMethod.POST,
                    requestEntity, Void.class);
            return ResponseEntity.ok("ROLE_ADMINISTRATOR");
        } catch (HttpClientErrorException.Unauthorized e) {
            // If the user is not an administrator, return unauthorized.
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}