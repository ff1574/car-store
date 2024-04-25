package com.rit.carstore.Repositories;

import com.rit.carstore.Entities.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// This is a repository interface for managing customer addresses in the system.
// It extends JpaRepository to leverage Spring Data JPA's methods for database operations.
@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Integer> {
}