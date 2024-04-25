package com.rit.carstore.Repositories;

import com.rit.carstore.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// This is a repository interface for managing customers in the system.
// It extends JpaRepository to leverage Spring Data JPA's methods for database operations.
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    // Method to find a customer by their email.
    // Spring Data JPA will automatically implement this method based on its name.
    Customer findByCustomerEmail(String customerEmail);
}