package com.rit.carstore.Services;

import com.rit.carstore.Entities.Administrator;
import com.rit.carstore.Repositories.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministratorService {

    private final AdministratorRepository administratorRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdministratorService(AdministratorRepository administratorRepository,
            BCryptPasswordEncoder passwordEncoder) {
        this.administratorRepository = administratorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Administrator> findAllAdministrators() {
        return administratorRepository.findAll();
    }

    public Optional<Administrator> findAdministratorById(Integer id) {
        return administratorRepository.findById(id);
    }

    public Administrator saveAdministrator(Administrator administrator) {
        String rawPassword = administrator.getAdministratorPassword(); // Capture the raw password
        String encodedPassword = passwordEncoder.encode(rawPassword); // Encode the raw password
        administrator.setAdministratorPassword(encodedPassword); // Set the encoded password
        return administratorRepository.save(administrator); // Save the administrator
    }

    public void deleteAdministrator(Integer id) {
        administratorRepository.deleteById(id);
    }

    public boolean checkPassword(String email, String rawPassword) {
        System.out.println("Attempting to find administrator with email: " + email);
        Administrator admin = administratorRepository.findByAdministratorEmail(email);

        if (admin == null) {
            System.out.println("No administrator found with email: " + email);
            return false;
        }

        System.out.println("Administrator found. Email: " + email);
        System.out.println("Stored hash in database for comparison: " + admin.getAdministratorPassword());
        System.out.println("Raw password provided for checking: " + rawPassword);

        boolean matches = passwordEncoder.matches(rawPassword, admin.getAdministratorPassword());

        if (matches) {
            System.out.println("Password matches for email: " + email);
        } else {
            System.out.println("Password DOES NOT match for email: " + email);
        }

        return matches;
    }

    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

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

    public void setInitialAdminPasswords(String defaultPassword) {
        List<Administrator> admins = findAllAdministrators();
        admins.forEach(admin -> {
            String encodedPassword = passwordEncoder.encode(defaultPassword);
            admin.setAdministratorPassword(encodedPassword);
            saveAdministrator(admin);
        });
    }
}
