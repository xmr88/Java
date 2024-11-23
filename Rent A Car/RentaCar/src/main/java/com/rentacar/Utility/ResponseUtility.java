package com.rentacar.Utility;

import com.rentacar.Entities.Car;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class ResponseUtility {

    // Message Constants for Cars
    public static final String CAR_UPDATED_SUCCESS = "Car updated successfully.";
    public static final String CAR_NOT_FOUND = "Car not found. No update was performed.";
    public static final String CAR_DELETION_SUCCESS = "Car deleted successfully.";
    public static final String CAR_DELETION_FAILED = "Failed to delete car. Car not found.";
    public static final String CAR_FETCH_SUCCESS = "Car information fetched successfully.";
    public static final String CAR_FETCH_FAILED = "Car information was not found.";
    public static final String CARS_FETCH_SUCCESS = "All cars fetched successfully.";
    public static final String CAR_CREATED_SUCCESS = "Car created successfully.";
    public static final String CAR_CREATED_FAILED = "Failed to create car.";




    // Generic Response Creation Method
    public static ResponseEntity<?> createResponse(String message, HttpStatus status, Object data) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("status", status.value());
        if (data != null) {
            response.put("data", data);
        }
        return new ResponseEntity<>(response, status);
    }

    // Generic Response for ERRORS, where no data is needed
    public static ResponseEntity<?> errorResponse(String message, HttpStatus status) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("status", status.value());
        return new ResponseEntity<>(response, status);
    }

    // Create a Car Success Response
    public static ResponseEntity<?> createCarCreatedResponse(Car car) {
        return createResponse(CAR_CREATED_SUCCESS, HttpStatus.CREATED, car);
    }

    // Create a Car Error Response
    public static ResponseEntity<?> createCarErrorResponse() {
        return errorResponse(CAR_CREATED_FAILED, HttpStatus.BAD_REQUEST);
    }

    // Fetch Single Car Success Response
    public static ResponseEntity<?> getSingleCarSuccessResponse(Object car) {
        return createResponse(CAR_FETCH_SUCCESS, HttpStatus.OK, car);
    }

    // Fetch Single Car Error Response
    public static ResponseEntity<?> getSingleCarFailedResponse() {
        return errorResponse(CAR_FETCH_FAILED, HttpStatus.NOT_FOUND);
    }

    // Car Update Success Response
    public static ResponseEntity<?> getCarUpdatedResponse() {
        return createResponse(CAR_UPDATED_SUCCESS, HttpStatus.OK, null);
    }

    // Car Update Error Response
    public static ResponseEntity<?> getCarUpdateFailedResponse() {
        return errorResponse(CAR_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    // Car Deletion Success Response
    public static ResponseEntity<?> getCarDeletedResponse() {
        return createResponse(CAR_DELETION_SUCCESS, HttpStatus.OK, null);
    }

    // Car Deletion Error Response
    public static ResponseEntity<?> getCarDeleteFailedResponse() {
        return errorResponse(CAR_DELETION_FAILED, HttpStatus.NOT_FOUND);
    }

    // Fetch All Cars Success Response
    public static ResponseEntity<?> getAllCarsSuccessResponse(Object cars) {
        return createResponse(CARS_FETCH_SUCCESS, HttpStatus.OK, cars);
    }
}
