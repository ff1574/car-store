package com.rit.carstore.Services;

import com.rit.carstore.Entities.Car;
import com.rit.carstore.Entities.Manufacturer;
import com.rit.carstore.Repositories.CarRepository;
import com.rit.carstore.Repositories.ManufacturerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// This is a service class for managing cars in the system.
@Service
public class CarService {

    // The repository for performing database operations on cars.
    private final CarRepository carRepository;

    // The repository for performing database operations on manufacturers.
    private final ManufacturerRepository manufacturerRepository;

    // Constructor injection of the repositories.
    @Autowired
    public CarService(CarRepository carRepository, ManufacturerRepository manufacturerRepository) {
        this.carRepository = carRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    // Method to find all cars.
    public List<Car> findAllCars() {
        return carRepository.findAll();
    }

    // Method to find a car by its ID.
    public Optional<Car> findCarById(Integer id) {
        return carRepository.findById(id);
    }

    // Method to save a new car. The car must not have an ID and the manufacturer
    // must exist.
    public Car saveNewCar(Car car, int manufacturerId) {
        if (car.getCarId() != null) {
            throw new IllegalArgumentException("New car must not have an ID");
        }
        Optional<Manufacturer> optionalManufacturer = manufacturerRepository.findById(manufacturerId);
        if (!optionalManufacturer.isPresent()) {
            throw new IllegalArgumentException("Manufacturer with ID " + manufacturerId + " does not exist");
        }
        car.setManufacturer(optionalManufacturer.get());
        return carRepository.save(car);
    }

    // Method to update a car. The car must have an ID and exist in the database.
    public Car updateCar(Car car) {
        if (car.getCarId() == null) {
            throw new IllegalArgumentException("Cannot update a car without an ID");
        }
        return carRepository.findById(car.getCarId())
                .map(existingCar -> {
                    existingCar.setCarModel(car.getCarModel());
                    existingCar.setCarYear(car.getCarYear());
                    existingCar.setCarMileage(car.getCarMileage());
                    existingCar.setCarPrice(car.getCarPrice());
                    existingCar.setCarColor(car.getCarColor());
                    existingCar.setCarEngine(car.getCarEngine());
                    existingCar.setCarStockQuantity(car.getCarStockQuantity());
                    existingCar.setManufacturer(existingCar.getManufacturer()); // Assume the Manufacturer is managed
                                                                                // correctly
                    return carRepository.save(existingCar);
                })
                .orElseThrow(() -> new IllegalArgumentException("Car with ID " + car.getCarId() + " does not exist."));
    }

    // Method to delete a car by its ID.
    public void deleteCar(Integer id) {
        carRepository.deleteById(id);
    }
}