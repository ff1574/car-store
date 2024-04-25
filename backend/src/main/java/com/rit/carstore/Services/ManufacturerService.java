package com.rit.carstore.Services;

import com.rit.carstore.Entities.Manufacturer;
import com.rit.carstore.Repositories.ManufacturerRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// This is a service class for managing manufacturers in the system.
@Service
public class ManufacturerService {

    // The repository for performing database operations on manufacturers.
    private final ManufacturerRepository manufacturerRepository;

    // Constructor injection of the repository.
    @Autowired
    public ManufacturerService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    // Method to find all manufacturers.
    public List<Manufacturer> findAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    // Method to find a manufacturer by their ID.
    public Optional<Manufacturer> findManufacturerById(Integer id) {
        return manufacturerRepository.findById(id);
    }

    // Method to save a manufacturer. If the manufacturer has an ID, it will be
    // updated. If it does not have an ID, it will be created.
    public Manufacturer saveManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    // Method to delete a manufacturer by their ID.
    public void deleteManufacturer(Integer id) {
        manufacturerRepository.deleteById(id);
    }

    // Method to update a manufacturer's image. The image is stored as a byte array.
    // This method is transactional, which means that it is part of a transaction.
    // If an error occurs during the transaction, all changes will be rolled back.
    @Transactional
    public void updateManufacturerImage(Integer manufacturerId, byte[] imageBytes) {
        manufacturerRepository.findById(manufacturerId).ifPresent(manufacturer -> {
            manufacturer.setManufacturerImage(imageBytes);
            manufacturerRepository.save(manufacturer);
        });
    }
}