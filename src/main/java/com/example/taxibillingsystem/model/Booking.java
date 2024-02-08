package com.example.taxibillingsystem.model;

import com.example.taxibillingsystem.enums.TaxiStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
