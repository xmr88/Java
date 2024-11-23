package com.rentacar.Mapper;

import com.rentacar.Entities.Car;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarRowMapper implements RowMapper<Car> {

    @Override
    public Car mapRow(ResultSet rs, int rowNum) throws SQLException {

        // Mapping ResultSet data to a Customer object
        Car car = new Car();
        car.setId((long) rs.getInt("id"));
        car.setMake(rs.getString("make"));
        car.setModel(rs.getString("model"));
        car.setLocation(rs.getString("location"));
        car.setPricePerDay(rs.getDouble("price_per_day"));
        car.setAvailable(rs.getBoolean("available"));

        return car;
    }
}
