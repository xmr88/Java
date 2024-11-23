package com.rentacar.Service;

import com.rentacar.Entities.Offer;
import com.rentacar.Repository.OfferRepository;
import com.rentacar.Repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final CarRepository carRepository;

    public OfferService(OfferRepository offerRepository, CarRepository carRepository) {
        this.offerRepository = offerRepository;
        this.carRepository = carRepository;
    }

    // Create a new offer
    public Offer createOffer(Offer offer) {
        // Ensure the car exists in the repository before creating the offer
        if (carRepository.existsById(offer.getCar().getId())) {
            return offerRepository.save(offer);
        }
        return null;  // Return null if the car does not exist
    }

    // Get all offers for a customer
    public List<Offer> getOffersByCustomer(String customerName) {
        return offerRepository.findByCustomerName(customerName);
    }

    // Get an offer by ID
    public Offer getOfferById(Long id) {
        Optional<Offer> offer = offerRepository.findById(id);
        return offer.orElse(null);  // Return the offer if found, otherwise null
    }

    // Delete an offer by ID
    public boolean deleteOffer(Long id) {
        if (offerRepository.existsById(id)) {
            offerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Accept an offer
    public boolean acceptOffer(Long id) {
        Optional<Offer> offer = offerRepository.findById(id);
        if (offer.isPresent()) {
            // Here you can add logic to mark the offer as accepted
            // For example, setting an "accepted" flag on the offer, or doing any other necessary actions
            return true;
        }
        return false;
    }
}
