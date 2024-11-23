package com.rentacar.Service;

import com.rentacar.Entities.Car;
import com.rentacar.Mapper.CarRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final JdbcTemplate dataBase;

    public CarService(JdbcTemplate jdbc) {
        this.dataBase = jdbc;
    }

    // Create new Car
    public boolean createCar(Car car) {
        String query = "INSERT INTO Cars (make, model, location, price_per_day, available) VALUES (?, ?, ?, ?, ?)";
        int rowsAffected = this.dataBase.update(query, car.getMake(), car.getModel(), car.getLocation(),
                car.getPricePerDay(), car.isAvailable());
        return rowsAffected > 0;
    }

    // Get all Cars
    public List<Car> getAllCars() {
        String query = "SELECT * FROM Cars";
        return this.dataBase.query(query, new CarRowMapper());
    }

    // Get Cars by location
    public List<Car> getCarsByLocation(String location) {
        String query = "SELECT * FROM Cars WHERE location = ?";
        return this.dataBase.query(query, new Object[]{location}, new CarRowMapper());
    }

    // Get Cars by id
    public List<Car> getCarById(Long id) {
        String query = "SELECT * FROM Cars WHERE id = ?";
        return this.dataBase.query(query, new Object[]{id}, new CarRowMapper());
    }


    // Update car
    public boolean updateCar(Car car) {
        String query = "UPDATE Cars SET make = ?, model = ?, location = ?, price_per_day = ?, available = ? WHERE id = ?";
        int rowsAffected = this.dataBase.update(query, car.getMake(), car.getModel(), car.getLocation(),
                car.getPricePerDay(), car.isAvailable(), car.getId());
        return rowsAffected > 0;
    }

    // Delete car by ID
    public boolean deleteCar(long id) {
        String query = "DELETE FROM Cars WHERE id = ?";
        int rowsAffected = this.dataBase.update(query, id);
        return rowsAffected > 0;
    }
}
