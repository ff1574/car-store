package com.rit.carstore.Controllers;

import com.rit.carstore.Entities.Car;
import com.rit.carstore.Services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<Car> getAllCars() {
        return carService.findAllCars();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Integer id) {
        return carService.findCarById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Car createCar(@RequestBody Car car) {
        return carService.saveNewCar(car);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Integer id, @RequestBody Car car) {
        return carService.findCarById(id)
                .map(existingCar -> {
                    car.setCarId(id);
                    return ResponseEntity.ok(carService.updateCar(car));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable Integer id) {
        return carService.findCarById(id)
                .map(car -> {
                    carService.deleteCar(id);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
