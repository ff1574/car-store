package com.rit.carstore.Controllers;

import com.rit.carstore.Entities.CustomerAddress;
import com.rit.carstore.Services.CustomerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customerAddress")
public class CustomerAddressController {

    private final CustomerAddressService customerAddressService;

    @Autowired
    public CustomerAddressController(CustomerAddressService customerAddressService) {
        this.customerAddressService = customerAddressService;
    }

    @GetMapping
    public List<CustomerAddress> getAllCustomerAddresses() {
        return customerAddressService.findAllAddresses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerAddress> getCustomerAddressById(@PathVariable Integer id) {
        return customerAddressService.findAddressById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public CustomerAddress createCustomerAddress(@RequestBody CustomerAddress customerAddress) {
        return customerAddressService.saveAddress(customerAddress);
    }

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
