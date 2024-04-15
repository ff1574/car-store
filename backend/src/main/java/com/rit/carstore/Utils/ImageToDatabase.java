/*
 * 
 * 
 * This is something that should be ran when setting up the project locally,
 * once in production, this should be removed or kept as a utility script
 */

// package com.rit.carstore.Utils;

// import java.io.File;
// import java.io.IOException;
// import java.nio.file.Files;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.SQLException;

// public class ImageToDatabase {
// private static final String DIRECTORY_PATH =
// "backend\\src\\main\\java\\com\\rit\\carstore\\Utils\\misc_utils";
// private static final String DB_URL =
// "jdbc:mysql://localhost:3306/cardatabase";

// // Change this to your MySQL username and password
// private static final String USER = "root";
// private static final String PASS = "admin";

// public static void main(String[] args) {
// File dir = new File(DIRECTORY_PATH);
// File[] directoryListing = dir.listFiles();
// if (directoryListing != null) {
// try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
// System.out.println("Connected to the database.");
// for (File child : directoryListing) {
// if (child.getName().endsWith(".png")) {
// // Extracting manufacturer ID from the file name
// String fileName = child.getName();
// int manufacturerId = Integer
// .parseInt(fileName.replace("manufacturer_", "").replace(".png", ""));
// System.out.println("Processing file: " + fileName);

// // Reading the image file into a byte array
// byte[] imageBytes = Files.readAllBytes(child.toPath());

// // Preparing the SQL statement
// String sql = "UPDATE manufacturers SET manufacturer_image = ? WHERE
// manufacturer_id = ?";
// try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
// // Using setBytes to directly store the binary data
// pstmt.setBytes(1, imageBytes);
// pstmt.setInt(2, manufacturerId);
// pstmt.executeUpdate();
// System.out.println("Updated image for manufacturer ID: " + manufacturerId);
// }
// }
// }
// } catch (SQLException | IOException e) {
// System.out.println("An error occurred: " + e.getMessage());
// e.printStackTrace();
// }
// } else {
// System.out.println("No files found in the directory.");
// }
// }
// }
