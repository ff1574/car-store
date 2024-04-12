package com.rit.carstore.Services;

import com.rit.carstore.Entities.CustomerAddress;
import com.rit.carstore.Repositories.CustomerAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerAddressService {

    private final CustomerAddressRepository customerAddressRepository;

    @Autowired
    public CustomerAddressService(CustomerAddressRepository customerAddressRepository) {
        this.customerAddressRepository = customerAddressRepository;
    }

    public List<CustomerAddress> findAllAddresses() {
        return customerAddressRepository.findAll();
    }

    public Optional<CustomerAddress> findAddressById(Integer id) {
        return customerAddressRepository.findById(id);
    }

    public CustomerAddress saveAddress(CustomerAddress customerAddress) {
        return customerAddressRepository.save(customerAddress);
    }

    public void deleteAddress(Integer id) {
        customerAddressRepository.deleteById(id);
    }
}
