package com.rit.carstore.Services;

import com.rit.carstore.Entities.Car;
import com.rit.carstore.Repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> findAllCars() {
        return carRepository.findAll();
    }

    public Optional<Car> findCarById(Integer id) {
        return carRepository.findById(id);
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    public void deleteCar(Integer id) {
        carRepository.deleteById(id);
    }
}
