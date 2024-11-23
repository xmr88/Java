package com.rentacar.Repository;

import com.rentacar.Entities.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    // Custom query to find all offers by customer name
    List<Offer> findByCustomerName(String customerName);

    // Custom query to find all offers for a specific car ID
    List<Offer> findByCar_Id(Long carId);
}
