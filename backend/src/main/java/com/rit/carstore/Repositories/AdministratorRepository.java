package com.rit.carstore.Repositories;

import com.rit.carstore.Entities.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// This is a repository interface for managing administrators in the system.
// It extends JpaRepository to leverage Spring Data JPA's methods for database operations.
@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {
    // Method to find an administrator by their email.
    // Spring Data JPA will automatically implement this method based on its name.
    Administrator findByAdministratorEmail(String administratorEmail);
}