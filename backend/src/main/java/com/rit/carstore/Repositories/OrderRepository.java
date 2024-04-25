package com.rit.carstore.Repositories;

import com.rit.carstore.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// This is a repository interface for managing orders in the system.
// It extends JpaRepository to leverage Spring Data JPA's methods for database operations.
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}