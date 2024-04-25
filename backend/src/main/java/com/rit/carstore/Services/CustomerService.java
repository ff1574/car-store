package com.rit.carstore.Services;

import com.rit.carstore.Entities.Customer;
import com.rit.carstore.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// This is a service class for managing customers in the system.
@Service
public class CustomerService {

    // The repository for performing database operations on customers.
    private final CustomerRepository customerRepository;

    // The password encoder for encoding passwords.
    private final BCryptPasswordEncoder passwordEncoder;

    // Constructor injection of the repository and password encoder.
    @Autowired
    public CustomerService(CustomerRepository customerRepository, BCryptPasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Method to find all customers.
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    // Method to find a customer by their ID.
    public Optional<Customer> findCustomerById(Integer id) {
        return customerRepository.findById(id);
    }

    // Method to save a customer. The password is encoded before saving.
    public Customer saveCustomer(Customer customer) {
        String encodedPassword = passwordEncoder.encode(customer.getCustomerPassword());
        customer.setCustomerPassword(encodedPassword);
        return customerRepository.save(customer);
    }

    // Method to delete a customer by their ID.
    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }

    // Method to check if a raw password matches the stored password for a customer.
    public boolean checkPassword(String email, String rawPassword) {
        Customer customer = customerRepository.findByCustomerEmail(email);

        if (customer == null) {
            return false;
        }

        boolean matches = passwordEncoder.matches(rawPassword, customer.getCustomerPassword());

        return matches;
    }

    // Method to encode a raw password.
    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    // Method to rehash all passwords. This is useful if the password encoding
    // strategy changes.
    public void rehashPasswords() {
        List<Customer> customers = findAllCustomers();
        customers.forEach(customer -> {
            String rawPassword = customer.getCustomerPassword(); // Assume this is the plain password for rehashing
            if (!rawPassword.startsWith("$2a$")) { // Check if already hashed
                String encodedPassword = passwordEncoder.encode(rawPassword);
                customer.setCustomerPassword(encodedPassword);
                saveCustomer(customer);
            }
        });
    }
}