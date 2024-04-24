package com.rit.carstore.Services;

import com.rit.carstore.Entities.Customer;
import com.rit.carstore.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, BCryptPasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findCustomerById(Integer id) {
        return customerRepository.findById(id);
    }

    public Customer saveCustomer(Customer customer) {
        String encodedPassword = passwordEncoder.encode(customer.getCustomerPassword());
        customer.setCustomerPassword(encodedPassword);
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }

    public boolean checkPassword(String email, String rawPassword) {
        System.out.println("Attempting to find customer with email: " + email);
        Customer customer = customerRepository.findByCustomerEmail(email);

        if (customer == null) {
            System.out.println("No customer found with email: " + email);
            return false;
        }

        System.out.println("Customer found. Email: " + email);
        System.out.println("Stored hash in database for comparison: " + customer.getCustomerPassword());
        System.out.println("Raw password provided for checking: " + rawPassword);

        boolean matches = passwordEncoder.matches(rawPassword, customer.getCustomerPassword());

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
