package com.rit.carstore.Controllers;

import com.rit.carstore.Entities.Manufacturer;
import com.rit.carstore.Services.ManufacturerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

// This is a REST controller for managing manufacturers in the system.
@RestController
@RequestMapping("/api/manufacturer")
public class ManufacturerController {

    // Logger for logging information and errors.
    private static final Logger logger = LoggerFactory.getLogger(ManufacturerController.class);

    // Service class for handling business logic related to manufacturers.
    private final ManufacturerService manufacturerService;

    // Dependency injection of the manufacturer service.
    @Autowired
    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    // Endpoint for getting all manufacturers.
    @GetMapping
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerService.findAllManufacturers();
    }

    // Endpoint for getting a manufacturer by their ID.
    @GetMapping("/{id}")
    public ResponseEntity<Manufacturer> getManufacturerById(@PathVariable Integer id) {
        return manufacturerService.findManufacturerById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint for creating a new manufacturer.
    @PostMapping
    public Manufacturer createManufacturer(@RequestBody Manufacturer manufacturer) {
        return manufacturerService.saveManufacturer(manufacturer);
    }

    // Endpoint for updating an existing manufacturer.
    @PutMapping("/{id}")
    public ResponseEntity<Manufacturer> updateManufacturer(@PathVariable Integer id,
            @RequestBody Manufacturer manufacturer) {
        return manufacturerService.findManufacturerById(id)
                .map(existingManufacturer -> {
                    manufacturer.setManufacturerId(id);
                    return ResponseEntity.ok(manufacturerService.saveManufacturer(manufacturer));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint for deleting a manufacturer.
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteManufacturer(@PathVariable Integer id) {
        return manufacturerService.findManufacturerById(id)
                .map(manufacturer -> {
                    manufacturerService.deleteManufacturer(id);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint for updating a manufacturer's image.
    @PutMapping("/{id}/image")
    public ResponseEntity<?> updateManufacturerImage(@PathVariable Integer id,
            @RequestParam("image") MultipartFile image) throws IOException {
        // Log the size of the received image.
        if (!image.isEmpty()) {
            logger.info("Received image with size: {} bytes", image.getSize());
        }

        return manufacturerService.findManufacturerById(id)
                .map(manufacturer -> {
                    try {
                        // Convert the image to a byte array and set it as the manufacturer's image.
                        byte[] imageBytes = image.getBytes();
                        logger.info("Converting image to byte array of size: {} bytes", imageBytes.length);
                        manufacturer.setManufacturerImage(imageBytes);
                        manufacturerService.saveManufacturer(manufacturer);
                        return ResponseEntity.ok().build();
                    } catch (IOException e) {
                        // If there was an error reading the image file, log the error and return a 500
                        // Internal Server Error response.
                        logger.error("Error reading image bytes", e);
                        return ResponseEntity.internalServerError().body("Error updating manufacturer image.");
                    }
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}