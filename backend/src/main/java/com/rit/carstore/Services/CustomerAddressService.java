package com.rit.carstore.Services;

import com.rit.carstore.Entities.CustomerAddress;
import com.rit.carstore.Repositories.CustomerAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// This is a service class for managing customer addresses in the system.
@Service
public class CustomerAddressService {

    // The repository for performing database operations on customer addresses.
    private final CustomerAddressRepository customerAddressRepository;

    // Constructor injection of the repository.
    @Autowired
    public CustomerAddressService(CustomerAddressRepository customerAddressRepository) {
        this.customerAddressRepository = customerAddressRepository;
    }

    // Method to find all customer addresses.
    public List<CustomerAddress> findAllAddresses() {
        return customerAddressRepository.findAll();
    }

    // Method to find a customer address by its ID.
    public Optional<CustomerAddress> findAddressById(Integer id) {
        return customerAddressRepository.findById(id);
    }

    // Method to save a customer address. If the address has an ID, it will be
    // updated. If it does not have an ID, it will be created.
    public CustomerAddress saveAddress(CustomerAddress customerAddress) {
        return customerAddressRepository.save(customerAddress);
    }

    // Method to delete a customer address by its ID.
    public void deleteAddress(Integer id) {
        customerAddressRepository.deleteById(id);
    }
}