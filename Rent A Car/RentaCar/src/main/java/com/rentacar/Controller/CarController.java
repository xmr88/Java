package com.rentacar.Controller;

import com.rentacar.Entities.Car;
import com.rentacar.Service.CarService;
import com.rentacar.Utility.ResponseUtility;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    // Create a new car
    @PostMapping
    public ResponseEntity<?> createNewCar(@RequestBody Car car) {
        boolean isCreated = carService.createCar(car);
        if (isCreated) {
            return ResponseUtility.createCarCreatedResponse(car);
        } else {
            return ResponseUtility.createCarCreatedResponse(car);
        }
    }


    // Fetch all existing cars
    @GetMapping
    public ResponseEntity<?> getAllCars() {
        List<Car> cars = carService.getAllCars();
        if (cars.isEmpty()) {
            return ResponseUtility.createCarErrorResponse();
        }
        return ResponseUtility.getAllCarsSuccessResponse(cars);
    }

    // Fetch cars by location
    @GetMapping("/location/{location}")
    public ResponseEntity<?> getCarsByLocation(@PathVariable String location) {
        List<Car> cars = carService.getCarsByLocation(location);
        if (cars.isEmpty()) {
            return ResponseUtility.createCarErrorResponse();
        }
        return ResponseUtility.getAllCarsSuccessResponse(cars);
    }

    // Fetch a single car by ID
    @GetMapping("/id/{id}")
    public ResponseEntity<?> fetchCarById(@PathVariable long id) {
        List<Car> cars = carService.getCarById(id);

        if (cars.isEmpty()) {
            return ResponseUtility.getSingleCarFailedResponse();
        }

        return ResponseUtility.getSingleCarSuccessResponse(cars);
    }

    // Update an existing car by ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCar(@PathVariable long id, @RequestBody Car car) {
        car.setId(id);
        boolean isUpdated = carService.updateCar(car);
        if (isUpdated) {
            return ResponseUtility.getCarUpdatedResponse();
        } else {
            return ResponseUtility.getCarUpdateFailedResponse();
        }
    }

    // Delete a car by  ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable long id) {
        boolean isDeleted = carService.deleteCar(id);
        if (isDeleted) {
            return ResponseUtility.getCarDeletedResponse();
        } else {
            return ResponseUtility.getCarDeleteFailedResponse();
        }
    }
}
