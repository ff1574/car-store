package com.rit.carstore.Repositories;

import com.rit.carstore.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByCustomerEmail(String customerEmail);
}
