package com.rentacar.Controller;

import com.rentacar.Entities.Offer;
import com.rentacar.Service.OfferService;
import com.rentacar.Utility.ResponseUtility;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    // Create a new offer
    @PostMapping
    public ResponseEntity<?> createOffer(@RequestBody Offer offer) {
        Offer createdOffer = offerService.createOffer(offer);
        if (createdOffer != null) {
            return ResponseUtility.createResponse(ResponseUtility.CAR_CREATED_SUCCESS, HttpStatus.CREATED, createdOffer);
        } else {
            return ResponseUtility.createResponse(ResponseUtility.CAR_CREATED_FAILED, HttpStatus.BAD_REQUEST, null);
        }
    }

    // Get all offers for a customer
    @GetMapping("/customer/{customerName}")
    public ResponseEntity<?> getOffersByCustomer(@PathVariable String customerName) {
        List<Offer> offers = offerService.getOffersByCustomer(customerName);
        if (offers.isEmpty()) {
            return ResponseUtility.createResponse(ResponseUtility.CAR_FETCH_FAILED, HttpStatus.NOT_FOUND, null);
        }
        return ResponseUtility.createResponse(ResponseUtility.CARS_FETCH_SUCCESS, HttpStatus.OK, offers);
    }

    // Get offer by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getOfferById(@PathVariable Long id) {
        Offer offer = offerService.getOfferById(id);
        if (offer == null) {
            return ResponseUtility.createResponse(ResponseUtility.CAR_FETCH_FAILED, HttpStatus.NOT_FOUND, null);
        }
        return ResponseUtility.createResponse(ResponseUtility.CAR_FETCH_SUCCESS, HttpStatus.OK, offer);
    }

    // Delete an offer by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOffer(@PathVariable Long id) {
        boolean isDeleted = offerService.deleteOffer(id);
        if (isDeleted) {
            return ResponseUtility.createResponse(ResponseUtility.CAR_DELETION_SUCCESS, HttpStatus.OK, null);
        }
        return ResponseUtility.createResponse(ResponseUtility.CAR_DELETION_FAILED, HttpStatus.NOT_FOUND, null);
    }

    // Accept an offer (Confirm the offer)
    @PutMapping("/accept/{id}")
    public ResponseEntity<?> acceptOffer(@PathVariable Long id) {
        boolean isAccepted = offerService.acceptOffer(id);
        if (isAccepted) {
            return ResponseUtility.createResponse("Offer accepted successfully", HttpStatus.OK, null);
        }
        return ResponseUtility.createResponse("Failed to accept the offer", HttpStatus.BAD_REQUEST, null);
    }
}
