package com.rit.carstore.Utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.rit.carstore.Repositories.AdministratorRepository;
import com.rit.carstore.Repositories.CustomerRepository;
import com.rit.carstore.Services.AdministratorService;
import com.rit.carstore.Services.CustomerService;
import com.rit.carstore.Entities.Administrator;
import com.rit.carstore.Entities.Customer;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final AdministratorRepository administratorRepository;
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public DatabaseInitializer(AdministratorRepository administratorRepository, BCryptPasswordEncoder passwordEncoder,
            AdministratorService administratorService, CustomerService customerService,
            CustomerRepository customerRepository) {
        this.administratorRepository = administratorRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Iterable<Administrator> admins = administratorRepository.findAll();
        boolean changes = false;
        for (Administrator admin : admins) {
            if (admin.getAdministratorPassword() == null || admin.getAdministratorPassword().isEmpty()) {
                // Set default password if not set
                String encodedPassword = passwordEncoder.encode("admin");
                admin.setAdministratorPassword(encodedPassword);
                administratorRepository.save(admin);
                changes = true;
            }
        }
        if (changes) {
            System.out.println("Default passwords set for some administrators.");
        } else {
            System.out.println("No administrators needed default passwords.");
        }

        Iterable<Customer> customers = customerRepository.findAll();
        changes = false;
        for (Customer customer : customers) {
            if (customer.getCustomerPassword().length() < 60) {
                System.out.println(customer.getCustomerPassword());
                String encodedPassword = passwordEncoder.encode(customer.getCustomerPassword());
                customer.setCustomerPassword(encodedPassword);
                customerRepository.save(customer);
                changes = true;
            }
        }
        if (changes) {
            System.out.println("Passwords encoded for customers.");
        } else {
            System.out.println("No customers needed password encoding.");
        }
    }
}
