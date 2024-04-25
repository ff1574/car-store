package com.rit.carstore.Utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

// This class implements CommandLineRunner, which means it will run after the application context is loaded.
// It is used to update the images for cars and manufacturers in the database.
@Component
public class ImageToDatabase implements CommandLineRunner {

    // The path to the directory containing the images.
    private static final String DIRECTORY_PATH = "backend/src/main/java/com/rit/carstore/Utils/misc_utils";

    // The JdbcTemplate is used to execute SQL queries.
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // This method is run after the application context is loaded.
    // It reads the images from the directory and updates the images in the
    // database.
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

    // This method updates the image for a car in the database.
    // It first checks if the image needs to be updated. If the image does not exist
    // or is empty, it updates the image.
    private void updateCarImage(int carId, File imageFile) throws IOException {
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

    // This method updates the image for a manufacturer in the database.
    // It first checks if the image needs to be updated. If the image does not exist
    // or is empty, it updates the image.
    private void updateManufacturerImage(int manufacturerId, File imageFile) throws IOException {
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