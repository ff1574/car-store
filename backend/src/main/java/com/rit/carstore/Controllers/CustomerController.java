package com.rit.carstore.Controllers;

import com.rit.carstore.Entities.Customer;
import com.rit.carstore.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.findAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer id) {
        return customerService.findCustomerById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Integer id, @RequestBody Customer customer) {
        return customerService.findCustomerById(id)
                .map(existingCustomer -> {
                    customer.setCustomerId(id);
                    return ResponseEntity.ok(customerService.saveCustomer(customer));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer id) {
        return customerService.findCustomerById(id)
                .map(customer -> {
                    customerService.deleteCustomer(id);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

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

    @PostMapping("/hash-password")
    public ResponseEntity<String> hashPassword(@RequestBody Map<String, String> passwordMap) {
        String password = passwordMap.get("password");
        if (password == null) {
            return ResponseEntity.badRequest().body("Password must be provided");
        }
        String hashedPassword = customerService.hashPassword(password);
        return ResponseEntity.ok(hashedPassword);
    }
}
