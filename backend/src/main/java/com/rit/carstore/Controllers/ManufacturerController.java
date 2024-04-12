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

@RestController
@RequestMapping("/api/manufacturer")
public class ManufacturerController {

    private static final Logger logger = LoggerFactory.getLogger(ManufacturerController.class);

    private final ManufacturerService manufacturerService;

    @Autowired
    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerService.findAllManufacturers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manufacturer> getManufacturerById(@PathVariable Integer id) {
        return manufacturerService.findManufacturerById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Manufacturer createManufacturer(@RequestBody Manufacturer manufacturer) {
        return manufacturerService.saveManufacturer(manufacturer);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteManufacturer(@PathVariable Integer id) {
        return manufacturerService.findManufacturerById(id)
                .map(manufacturer -> {
                    manufacturerService.deleteManufacturer(id);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity<?> updateManufacturerImage(@PathVariable Integer id,
            @RequestParam("image") MultipartFile image) throws IOException {
        if (!image.isEmpty()) {
            logger.info("Received image with size: {} bytes", image.getSize());
        }

        return manufacturerService.findManufacturerById(id)
                .map(manufacturer -> {
                    try {
                        byte[] imageBytes = image.getBytes();
                        logger.info("Converting image to byte array of size: {} bytes", imageBytes.length);
                        manufacturer.setManufacturerImage(imageBytes);
                        manufacturerService.saveManufacturer(manufacturer);
                        return ResponseEntity.ok().build();
                    } catch (IOException e) {
                        logger.error("Error reading image bytes", e);
                        return ResponseEntity.internalServerError().body("Error updating manufacturer image.");
                    }
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
