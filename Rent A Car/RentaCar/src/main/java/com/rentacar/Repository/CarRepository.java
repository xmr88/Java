package com.rentacar.Repository;

import com.rentacar.Entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    // Find all cars by location
    List<Car> findByLocation(String location);

    // You can also add custom methods like finding a car by its make and model
    List<Car> findByMakeAndModel(String make, String model);

    // Find a car by its ID (this is already supported by JpaRepository but added for clarity)
    Car findById(long id);
}
