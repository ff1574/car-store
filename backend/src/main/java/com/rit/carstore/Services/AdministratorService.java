package com.rit.carstore.Services;

import com.rit.carstore.Entities.Administrator;
import com.rit.carstore.Repositories.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// This is a service class for managing administrators in the system.
@Service
public class AdministratorService {

    // The repository for performing database operations on administrators.
    private final AdministratorRepository administratorRepository;

    // The password encoder for encoding passwords.
    private final BCryptPasswordEncoder passwordEncoder;

    // Constructor injection of the repository and password encoder.
    @Autowired
    public AdministratorService(AdministratorRepository administratorRepository,
            BCryptPasswordEncoder passwordEncoder) {
        this.administratorRepository = administratorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Method to find all administrators.
    public List<Administrator> findAllAdministrators() {
        return administratorRepository.findAll();
    }

    // Method to find an administrator by their ID.
    public Optional<Administrator> findAdministratorById(Integer id) {
        return administratorRepository.findById(id);
    }

    // Method to save an administrator. The password is encoded before saving.
    public Administrator saveAdministrator(Administrator administrator) {
        String rawPassword = administrator.getAdministratorPassword(); // Capture the raw password
        String encodedPassword = passwordEncoder.encode(rawPassword); // Encode the raw password
        administrator.setAdministratorPassword(encodedPassword); // Set the encoded password
        return administratorRepository.save(administrator); // Save the administrator
    }

    // Method to delete an administrator by their ID.
    public void deleteAdministrator(Integer id) {
        administratorRepository.deleteById(id);
    }

    // Method to check if a raw password matches the stored password for an
    // administrator.
    public boolean checkPassword(String email, String rawPassword) {
        Administrator admin = administratorRepository.findByAdministratorEmail(email);

        if (admin == null) {
            return false;
        }

        boolean matches = passwordEncoder.matches(rawPassword, admin.getAdministratorPassword());

        return matches;
    }

    // Method to encode a raw password.
    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    // Method to rehash all passwords. This is useful if the password encoding
    // strategy changes.
    public void rehashPasswords() {
        List<Administrator> administrators = findAllAdministrators();
        administrators.forEach(admin -> {
            String rawPassword = admin.getAdministratorPassword(); // Assume this is the plain password for rehashing
            if (!rawPassword.startsWith("$2a$")) { // Check if already hashed
                String encodedPassword = passwordEncoder.encode(rawPassword);
                admin.setAdministratorPassword(encodedPassword);
                saveAdministrator(admin);
            }
        });
    }

    // Method to set the initial passwords for all administrators to a default
    // password.
    public void setInitialAdminPasswords(String defaultPassword) {
        List<Administrator> admins = findAllAdministrators();
        admins.forEach(admin -> {
            String encodedPassword = passwordEncoder.encode(defaultPassword);
            admin.setAdministratorPassword(encodedPassword);
            saveAdministrator(admin);
        });
    }
}