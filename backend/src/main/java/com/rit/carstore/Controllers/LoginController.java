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

@RestController
@RequestMapping("/api/login")
public class LoginController {
    private final RestTemplate restTemplate;

    public LoginController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        if (email == null || password == null) {
            return ResponseEntity.badRequest().body("Email and password must be provided");
        }

        // Create request entity
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(credentials);

        // Check customer
        try {
            restTemplate.exchange("http://localhost:8080/api/customer/check-password", HttpMethod.POST, requestEntity, Void.class);
            return ResponseEntity.ok("ROLE_CUSTOMER");
        } catch (HttpClientErrorException.Unauthorized e) {
            // Ignore, try next
        }

        // Check administrator
        try {
            restTemplate.exchange("http://localhost:8080/api/administrator/check-password", HttpMethod.POST, requestEntity, Void.class);
            return ResponseEntity.ok("ROLE_ADMINISTRATOR");
        } catch (HttpClientErrorException.Unauthorized e) {
            // Ignore, return unauthorized
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
