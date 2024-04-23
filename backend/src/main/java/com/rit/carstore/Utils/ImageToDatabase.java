package com.rit.carstore.Utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
public class ImageToDatabase implements CommandLineRunner {

    private static final String DIRECTORY_PATH = "backend/src/main/java/com/rit/carstore/Utils/misc_utils";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        File dir = new File(DIRECTORY_PATH);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                String fileName = child.getName();
                if (fileName.endsWith(".png")) {
                    if (fileName.startsWith("manufacturer_")) {
                        int manufacturerId = Integer
                                .parseInt(fileName.replace("manufacturer_", "").replace(".png", ""));
                        updateManufacturerImage(manufacturerId, child);
                    } else if (fileName.startsWith("car_")) {
                        int carId = Integer.parseInt(fileName.replace("car_", "").replace(".png", ""));
                        updateCarImage(carId, child);
                    }
                }
            }
        } else {
            System.out.println("No files found in the directory.");
        }
    }

    private void updateCarImage(int carId, File imageFile) throws IOException {
        // Check if the image needs to be updated
        String sqlCheck = "SELECT car_image FROM cars WHERE car_id = ?";
        @SuppressWarnings("deprecation")
        byte[] existingImage = jdbcTemplate.queryForObject(sqlCheck, new Object[] { carId }, byte[].class);

        if (existingImage == null || existingImage.length == 0) {
            byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
            String sqlUpdate = "UPDATE cars SET car_image = ? WHERE car_id = ?";
            jdbcTemplate.update(sqlUpdate, imageBytes, carId);
            System.out.println("Updated image for car ID: " + carId);
        } else {
            System.out.println("Image already exists for car ID: " + carId + " - Skipping update.");
        }
    }

    private void updateManufacturerImage(int manufacturerId, File imageFile) throws IOException {
        // Check if the image needs to be updated
        String sqlCheck = "SELECT manufacturer_image FROM manufacturers WHERE manufacturer_id = ?";
        @SuppressWarnings("deprecation")
        byte[] existingImage = jdbcTemplate.queryForObject(sqlCheck, new Object[] { manufacturerId }, byte[].class);

        if (existingImage == null || existingImage.length == 0) {
            byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
            String sqlUpdate = "UPDATE manufacturers SET manufacturer_image = ? WHERE manufacturer_id = ?";
            jdbcTemplate.update(sqlUpdate, imageBytes, manufacturerId);
            System.out.println("Updated image for manufacturer ID: " + manufacturerId);
        } else {
            System.out.println("Image already exists for manufacturer ID: " + manufacturerId + " - Skipping update.");
        }
    }
}
