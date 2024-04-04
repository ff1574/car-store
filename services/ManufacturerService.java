package services;

import java.util.List;

import database.ManufacturerDAO;
import model.Manufacturer;

public class ManufacturerService {
    private ManufacturerDAO manufacturerDAO = new ManufacturerDAO();

    public List<Manufacturer> getAllManufacturers() {
        return manufacturerDAO.findAll();
    }
}
