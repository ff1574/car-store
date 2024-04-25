package com.rit.carstore.Controllers;

import com.rit.carstore.Entities.Administrator;
import com.rit.carstore.Services.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// This is a REST controller for managing administrators in the system.
@RestController
@RequestMapping("/api/administrator")
public class AdministratorController {

    // Service class for handling business logic related to administrators.
    private final AdministratorService administratorService;

    // Dependency injection of the administrator service.
    @Autowired
    public AdministratorController(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    // Endpoint for getting all administrators.
    @GetMapping
    public List<Administrator> getAllAdministrators() {
        return administratorService.findAllAdministrators();
    }

    // Endpoint for getting an administrator by their ID.
    @GetMapping("/{id}")
    public ResponseEntity<Administrator> getAdministratorById(@PathVariable Integer id) {
        return administratorService.findAdministratorById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint for creating a new administrator.
    @PostMapping
    public Administrator createAdministrator(@RequestBody Administrator administrator) {
        return administratorService.saveAdministrator(administrator);
    }

    // Endpoint for updating an existing administrator.
    @PutMapping("/{id}")
    public ResponseEntity<Administrator> updateAdministrator(@PathVariable Integer id,
            @RequestBody Administrator administrator) {
        return administratorService.findAdministratorById(id)
                .map(existingAdministrator -> {
                    administrator.setAdministratorId(id);
                    return ResponseEntity.ok(administratorService.saveAdministrator(administrator));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint for deleting an administrator.
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdministrator(@PathVariable Integer id) {
        return administratorService.findAdministratorById(id)
                .map(administrator -> {
                    administratorService.deleteAdministrator(id);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint for checking if a password is correct for a given email.
    @PostMapping("/check-password")
    public ResponseEntity<?> checkPassword(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        if (email == null || password == null) {
            System.out.println("Email and password must be provided");
            return ResponseEntity.badRequest().body("Email and password must be provided");
        }

        boolean passwordCorrect = administratorService.checkPassword(email, password);

        if (passwordCorrect) {
            System.out.println("Password correct");
            return ResponseEntity.ok().build();
        } else {
            System.out.println("Password incorrect");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // Endpoint for hashing a password.
    @PostMapping("/hash-password")
    public ResponseEntity<String> hashPassword(@RequestBody Map<String, String> passwordMap) {
        String password = passwordMap.get("password");
        if (password == null) {
            return ResponseEntity.badRequest().body("Password must be provided");
        }
        String hashedPassword = administratorService.hashPassword(password);
        return ResponseEntity.ok(hashedPassword);
    }

    // Endpoint for rehashing all passwords in the system.
    @PostMapping("/rehash-passwords")
    public ResponseEntity<?> rehashAllPasswords() {
        try {
            administratorService.rehashPasswords();
            return ResponseEntity.ok("Passwords rehashed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error rehashing passwords");
        }
    }

    // Endpoint for setting initial passwords for all administrators.
    @PostMapping("/set-initial-passwords")
    public ResponseEntity<?> setInitialAdminPasswords() {
        try {
            administratorService.setInitialAdminPasswords("admin"); // Set all passwords to "admin"
            return ResponseEntity.ok("Initial passwords set successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to set initial passwords.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}