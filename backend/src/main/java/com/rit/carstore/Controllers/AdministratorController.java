package com.rit.carstore.Controllers;

import com.rit.carstore.Entities.Administrator;
import com.rit.carstore.Services.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}