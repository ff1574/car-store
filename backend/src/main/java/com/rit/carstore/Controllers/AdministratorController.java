package com.rit.carstore.Controllers;

import com.rit.carstore.Entities.Administrator;
import com.rit.carstore.Services.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/administrator")
public class AdministratorController {

    private final AdministratorService administratorService;

    @Autowired
    public AdministratorController(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    @GetMapping
    public List<Administrator> getAllAdministrators() {
        return administratorService.findAllAdministrators();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administrator> getAdministratorById(@PathVariable Integer id) {
        return administratorService.findAdministratorById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Administrator createAdministrator(@RequestBody Administrator administrator) {
        return administratorService.saveAdministrator(administrator);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdministrator(@PathVariable Integer id) {
        return administratorService.findAdministratorById(id)
                .map(administrator -> {
                    administratorService.deleteAdministrator(id);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

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
}