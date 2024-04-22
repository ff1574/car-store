package com.rit.carstore.Services;

import com.rit.carstore.Entities.Car;
import com.rit.carstore.Repositories.CarRepository;
import com.rit.carstore.Repositories.ManufacturerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final ManufacturerRepository manufacturerRepository;

    @Autowired
    public CarService(CarRepository carRepository, ManufacturerRepository manufacturerRepository) {
        this.carRepository = carRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    public List<Car> findAllCars() {
        return carRepository.findAll();
    }

    public Optional<Car> findCarById(Integer id) {
        return carRepository.findById(id);
    }

    public Car saveNewCar(Car car) {
        if (car.getCarId() != null) {
            throw new IllegalArgumentException("New car must not have an ID");
        }
        return carRepository.save(car);
    }

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
                    existingCar.setManufacturer(existingCar.getManufacturer()); // Assume the Manufacturer is managed correctly
                    System.out.println("Car: " + existingCar.toString());
                    return carRepository.save(existingCar);
                })
                .orElseThrow(() -> new IllegalArgumentException("Car with ID " + car.getCarId() + " does not exist."));
    }

    public void deleteCar(Integer id) {
        carRepository.deleteById(id);
    }
}
