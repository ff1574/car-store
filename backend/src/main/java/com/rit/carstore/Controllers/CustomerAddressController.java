package com.rit.carstore.Controllers;

import com.rit.carstore.Entities.CustomerAddress;
import com.rit.carstore.Services.CustomerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// This is a REST controller for managing customer addresses in the system.
@RestController
@RequestMapping("/api/customerAddress")
public class CustomerAddressController {

    // Service class for handling business logic related to customer addresses.
    private final CustomerAddressService customerAddressService;

    // Dependency injection of the customer address service.
    @Autowired
    public CustomerAddressController(CustomerAddressService customerAddressService) {
        this.customerAddressService = customerAddressService;
    }

    // Endpoint for getting all customer addresses.
    @GetMapping
    public List<CustomerAddress> getAllCustomerAddresses() {
        return customerAddressService.findAllAddresses();
    }

    // Endpoint for getting a customer address by its ID.
    @GetMapping("/{id}")
    public ResponseEntity<CustomerAddress> getCustomerAddressById(@PathVariable Integer id) {
        return customerAddressService.findAddressById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint for creating a new customer address.
    @PostMapping
    public CustomerAddress createCustomerAddress(@RequestBody CustomerAddress customerAddress) {
        return customerAddressService.saveAddress(customerAddress);
    }

    // Endpoint for updating an existing customer address.
    @PutMapping("/{id}")
    public ResponseEntity<CustomerAddress> updateCustomerAddress(@PathVariable Integer id,
            @RequestBody CustomerAddress customerAddress) {
        return customerAddressService.findAddressById(id)
                .map(existingAddress -> {
                    customerAddress.setCustomerAddressId(id);
                    return ResponseEntity.ok(customerAddressService.saveAddress(customerAddress));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint for deleting a customer address.
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomerAddress(@PathVariable Integer id) {
        return customerAddressService.findAddressById(id)
                .map(address -> {
                    customerAddressService.deleteAddress(id);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}