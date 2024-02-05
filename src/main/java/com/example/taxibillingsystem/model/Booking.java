package com.example.taxibillingsystem.model;


import com.example.taxibillingsystem.contract.response.TaxiResponse;
import com.example.taxibillingsystem.contract.response.UserResponse;
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
    @JoinColumn(name = "users")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "taxis")
    private Taxi taxiId;

    private String pickupLocation;
    private String dropOffLocation;
    private int fare;

    private LocalDateTime bookingTime;

    @Enumerated(EnumType.STRING)
    private TaxiStatus status;
}
