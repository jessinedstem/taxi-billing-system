package com.example.taxibillingsystem.model;


import com.example.taxibillingsystem.contract.response.TaxiResponse;
import com.example.taxibillingsystem.contract.response.UserResponse;
import com.example.taxibillingsystem.validation.ValidBooking;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long bookingId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "taxi_id")
    private Taxi taxiId;

    private String pickupLocation;
    private String dropOffLocation;
    private double fare;

    private LocalDateTime bookingTime;

    @Enumerated(EnumType.STRING)
    private TaxiStatus status;
}
