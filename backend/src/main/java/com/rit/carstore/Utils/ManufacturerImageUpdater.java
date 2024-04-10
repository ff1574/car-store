package com.rit.carstore.Utils;

import com.rit.carstore.Entities.Manufacturer;
import com.rit.carstore.Services.ManufacturerService;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

import java.io.*;
import java.util.*;

@Component
public class ManufacturerImageUpdater {

    private final ManufacturerService manufacturerService;

    @Autowired
    public ManufacturerImageUpdater(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @PostConstruct
    public void updateManufacturerImages() {
        File directory = new File("./misc_utils"); // Set your directory path
        File[] files = directory.listFiles((dir, name) -> name.startsWith("manufacturer_") && name.endsWith(".png"));

        if (files != null) {
            for (File file : files) {
                try {
                    String name = file.getName();
                    int id = Integer.parseInt(name.substring(name.indexOf('_') + 1, name.indexOf('.')));
                    byte[] imageBytes = FileUtils.readFileToByteArray(file);

                    Optional<Manufacturer> manufacturerOptional = manufacturerService.findManufacturerById(id);
                    manufacturerOptional.ifPresent(manufacturer -> {
                        manufacturer.setManufacturerImage(imageBytes);
                        manufacturerService.saveManufacturer(manufacturer);
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing manufacturer ID from file name: " + file.getName());
                }
            }
        }
    }
}
