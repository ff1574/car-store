package com.rit.carstore.Controllers;

import com.rit.carstore.Entities.Customer;
import com.rit.carstore.Repositories.CustomerRepository;
import com.rit.carstore.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// This is a REST controller for managing customers in the system.
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    // Service class for handling business logic related to customers.
    private final CustomerService customerService;
    // Repository class for accessing the database for customer-related operations.
    private final CustomerRepository customerRepository;

    // Dependency injection of the customer service and repository.
    @Autowired
    public CustomerController(CustomerService customerService, CustomerRepository customerRepository) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }

    // Endpoint for getting all customers.
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.findAllCustomers();
    }

    // Endpoint for getting a customer by their ID.
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer id) {
        return customerService.findCustomerById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint for creating a new customer.
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    // Endpoint for updating an existing customer.
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Integer id, @RequestBody Customer customer) {
        return customerService.findCustomerById(id)
                .map(existingCustomer -> {
                    customer.setCustomerId(id);
                    return ResponseEntity.ok(customerService.saveCustomer(customer));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint for deleting a customer.
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer id) {
        return customerService.findCustomerById(id)
                .map(customer -> {
                    customerService.deleteCustomer(id);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint for checking if a password is correct for a given email.
    @PostMapping("/check-password")
    public ResponseEntity<?> checkPassword(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        if (email == null || password == null) {
            System.out.println("Email and password must be provided");
            return ResponseEntity.badRequest().body("Email and password must be provided");
        }

        boolean passwordCorrect = customerService.checkPassword(email, password);

        if (passwordCorrect) {
            System.out.println("Password correct");
            return ResponseEntity.ok().build();
        } else {
            System.out.println("Password incorrect");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // Endpoint for hashing a password.
    @PostMapping("/hash-password")
    public ResponseEntity<String> hashPassword(@RequestBody Map<String, String> passwordMap) {
        String password = passwordMap.get("password");
        if (password == null) {
            return ResponseEntity.badRequest().body("Password must be provided");
        }
        String hashedPassword = customerService.hashPassword(password);
        return ResponseEntity.ok(hashedPassword);
    }

    // Endpoint for getting a customer's ID by their email.
    @GetMapping("/email")
    public ResponseEntity<Integer> getCustomerIdByEmail(@RequestParam("email") String email) {
        Customer customer = customerRepository.findByCustomerEmail(email);
        if (customer.getCustomerId() != null) {
            return ResponseEntity.ok(customer.getCustomerId());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}