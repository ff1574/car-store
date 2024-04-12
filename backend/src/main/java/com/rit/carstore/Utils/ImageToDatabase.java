// import java.io.File;
// import java.io.FileInputStream;
// import java.io.IOException;
// import java.nio.file.Files;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.SQLException;

// // Used for inserting images into the database as blobs
// public class ImageToDatabase {
//     private static final String DIRECTORY_PATH = "misc_utils";
//     private static final String DB_URL = "jdbc:mysql://localhost:3306/cardatabase";
//     private static final String USER = "root";
//     private static final String PASS = "admin";

//     public static void main(String[] args) {
//         File dir = new File(DIRECTORY_PATH);
//         File[] directoryListing = dir.listFiles();
//         if (directoryListing != null) {
//             try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
//                 for (File child : directoryListing) {
//                     if (child.getName().endsWith(".png")) {
//                         // Extracting manufacturer ID from the file name
//                         String fileName = child.getName();
//                         int manufacturerId = Integer.parseInt(fileName.replace("manufacturer_", "").replace(".png", ""));
                        
//                         // Reading the image file into a byte array
//                         byte[] imageBytes = Files.readAllBytes(child.toPath());
                        
//                         // Preparing the SQL statement
//                         String sql = "UPDATE manufacturers SET manufacturer_image = ? WHERE manufacturer_id = ?";
//                         try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//                             pstmt.setBytes(1, imageBytes);
//                             pstmt.setInt(2, manufacturerId);
//                             pstmt.executeUpdate();
//                         }
//                     }
//                 }
//             } catch (SQLException | IOException e) {
//                 e.printStackTrace();
//             }
//         }
//     }
// }
