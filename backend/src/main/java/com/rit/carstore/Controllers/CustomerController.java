package com.rit.carstore.Controllers;

import com.rit.carstore.Entities.Customer;
import com.rit.carstore.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
