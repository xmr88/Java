package com.rentacar.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String location;  // Location where the car is available (e.g., Sofia, Plovdiv)

    @Column(nullable = false)
    private double pricePerDay;

    @Column(nullable = false)
    private boolean available;  // Availability status of the car
}
