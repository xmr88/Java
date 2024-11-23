package com.rentacar.Mapper;

import com.rentacar.Entities.Car;
import com.rentacar.Entities.Offer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OfferRowMapper implements RowMapper<Offer> {

    @Override
    public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Offer offer = new Offer();
        offer.setId(rs.getLong("id"));
        offer.setCustomerName(rs.getString("customer_name"));
        offer.setCustomerPhone(rs.getString("customer_phone"));
        offer.setRentalDays(rs.getInt("rental_days"));
        offer.setTotalPrice(rs.getDouble("total_price"));

        Car car = new Car();
        car.setId(rs.getLong("car_id"));
        offer.setCar(car);
        return offer;
    }
}
