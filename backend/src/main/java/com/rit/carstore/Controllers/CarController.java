package com.rit.carstore.Controllers;

import com.rit.carstore.Entities.Car;
import com.rit.carstore.Services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

// This is a REST controller for managing cars in the system.
@RestController
@RequestMapping("/api/car")
public class CarController {

    // Service class for handling business logic related to cars.
    private final CarService carService;

    // Dependency injection of the car service.
    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    // Endpoint for getting all cars.
    @GetMapping
    public List<Car> getAllCars() {
        return carService.findAllCars();
    }

    // Endpoint for getting a car by its ID.
    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Integer id) {
        return carService.findCarById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint for creating a new car. The car's details are provided as form data.
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Car> createCar(@RequestParam("carModel") String carModel,
            @RequestParam("carYear") Integer carYear,
            @RequestParam("carMileage") Integer carMileage,
            @RequestParam("carPrice") BigDecimal carPrice,
            @RequestParam("carColor") String carColor,
            @RequestParam("carEngine") String carEngine,
            @RequestParam("carStockQuantity") Integer carStockQuantity,
            @RequestParam("manufacturerId") int manufacturerId,
            @RequestParam(value = "carImage", required = false) MultipartFile carImage) {
        Car car = new Car();
        car.setCarModel(carModel);
        car.setCarYear(carYear);
        car.setCarMileage(carMileage);
        car.setCarPrice(carPrice);
        car.setCarColor(carColor);
        car.setCarEngine(carEngine);
        car.setCarStockQuantity(carStockQuantity);

        // If an image file was provided, set it as the car's image.
        if (carImage != null && !carImage.isEmpty()) {
            try {
                car.setCarImage(carImage.getBytes());
            } catch (IOException e) {
                // If there was an error reading the image file, return a 500 Internal Server
                // Error response.
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }

        return ResponseEntity.ok(carService.saveNewCar(car, manufacturerId));
    }

    // Endpoint for updating an existing car. The car's new details are provided as
    // form data.
    @SuppressWarnings({ "unchecked", "unused" })
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<Car> updateCar(@PathVariable Integer id,
            @RequestParam("carModel") String carModel,
            @RequestParam("carYear") Integer carYear,
            @RequestParam("carMileage") Integer carMileage,
            @RequestParam("carPrice") BigDecimal carPrice,
            @RequestParam("carColor") String carColor,
            @RequestParam("carEngine") String carEngine,
            @RequestParam("carStockQuantity") Integer carStockQuantity,
            @RequestParam(value = "carImage", required = false) MultipartFile carImage) {
        return (ResponseEntity<Car>) carService.findCarById(id)
                .map(existingCar -> {
                    existingCar.setCarModel(carModel);
                    existingCar.setCarYear(carYear);
                    existingCar.setCarMileage(carMileage);
                    existingCar.setCarPrice(carPrice);
                    existingCar.setCarColor(carColor);
                    existingCar.setCarEngine(carEngine);
                    existingCar.setCarStockQuantity(carStockQuantity);

                    // If an image file was provided, set it as the car's image.
                    if (carImage != null && !carImage.isEmpty()) {
                        try {
                            existingCar.setCarImage(carImage.getBytes());
                        } catch (IOException e) {
                            // If there was an error reading the image file, return a 500 Internal Server
                            // Error response.
                            return ResponseEntity.<Car>status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                        }
                    }

                    Car updatedCar = carService.updateCar(existingCar);
                    return ResponseEntity.ok(updatedCar);
                })
                .orElseGet(() -> ResponseEntity.<Car>notFound().build());
    }

    // Endpoint for deleting a car.
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