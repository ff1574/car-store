package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Manufacturer;

public class ManufacturerDAO {
    public List<Manufacturer> findAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String sql = "SELECT * FROM manufacturers";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setManufacturerId(rs.getInt("manufacturer_id"));
                manufacturer.setManufacturerName(rs.getString("manufacturer_name"));
                manufacturer.setManufacturerCountry(rs.getString("manufacturer_country"));
                manufacturer.setManufacturerWebsite(rs.getString("manufacturer_website"));
                manufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return manufacturers;
    }
}
