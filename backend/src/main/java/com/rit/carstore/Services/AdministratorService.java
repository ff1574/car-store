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
        String encodedPassword = passwordEncoder.encode(administrator.getAdministratorPassword());
        administrator.setAdministratorPassword(encodedPassword);
        return administratorRepository.save(administrator);
    }

    public void deleteAdministrator(Integer id) {
        administratorRepository.deleteById(id);
    }

    public boolean checkPassword(String email, String rawPassword) {
        Administrator admin = administratorRepository.findByAdministratorEmail(email);
        if (admin == null) {
            System.out.println("No administrator found with email: " + email);
            return false;
        }

        boolean matches = passwordEncoder.matches(rawPassword, admin.getAdministratorPassword());

        if (matches) {
            System.out.println("Password matches for email: " + email);
        } else {
            System.out.println("Password does not match for email: " + email);
        }

        return matches;
    }

    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}