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

        if (carImage != null && !carImage.isEmpty()) {
            try {
                car.setCarImage(carImage.getBytes());
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }

        return ResponseEntity.ok(carService.saveNewCar(car, manufacturerId));
    }

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
    
                    if (carImage != null && !carImage.isEmpty()) {
                        try {
                            existingCar.setCarImage(carImage.getBytes());
                        } catch (IOException e) {
                            // Return a ResponseEntity with HttpStatus.INTERNAL_SERVER_ERROR
                            return ResponseEntity.<Car>status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                        }
                    }
    
                    Car updatedCar = carService.updateCar(existingCar);
                    return ResponseEntity.ok(updatedCar);
                })
                .orElseGet(() -> ResponseEntity.<Car>notFound().build());
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
