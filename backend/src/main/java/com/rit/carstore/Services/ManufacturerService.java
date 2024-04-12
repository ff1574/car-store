package com.rit.carstore.Services;

import com.rit.carstore.Entities.Manufacturer;
import com.rit.carstore.Repositories.ManufacturerRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    @Autowired
    public ManufacturerService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public List<Manufacturer> findAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    public Optional<Manufacturer> findManufacturerById(Integer id) {
        return manufacturerRepository.findById(id);
    }

    public Manufacturer saveManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    public void deleteManufacturer(Integer id) {
        manufacturerRepository.deleteById(id);
    }

    @Transactional
    public void updateManufacturerImage(Integer manufacturerId, byte[] imageBytes) {
        manufacturerRepository.findById(manufacturerId).ifPresent(manufacturer -> {
            manufacturer.setManufacturerImage(imageBytes);
            manufacturerRepository.save(manufacturer);
        });
    }
}
