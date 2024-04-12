package com.rit.carstore.Services;

import com.rit.carstore.Repositories.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class ManufacturerImageUpdateService {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    private static final String DIRECTORY_PATH = "C:/Faks/Spring 2/ISTE330/car-store/backend/src/main/java/com/rit/carstore/Utils/misc_utils";

    @PostConstruct // or use another trigger to start this method
    public void updateManufacturerImages() {
        File directory = new File(DIRECTORY_PATH);
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".png"));
        if (files != null) {
            for (File file : files) {
                try {
                    byte[] imageBytes = Files.readAllBytes(file.toPath());
                    String fileName = file.getName();
                    Integer manufacturerId = Integer
                            .parseInt(fileName.replace("manufacturer_", "").replace(".png", ""));
                    updateManufacturerImage(manufacturerId, imageBytes);
                } catch (IOException | NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Transactional
    public void updateManufacturerImage(Integer manufacturerId, byte[] imageBytes) {
        manufacturerRepository.findById(manufacturerId).ifPresent(manufacturer -> {
            manufacturer.setManufacturerImage(imageBytes);
            manufacturerRepository.save(manufacturer);
        });
    }
}
