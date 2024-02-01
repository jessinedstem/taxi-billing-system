package com.example.taxibillingsystem.contract.request;


import com.example.taxibillingsystem.model.TaxiStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class BookingRequest {
    private UserRequest user;
    private TaxiRequest taxi;
    @NotEmpty(message = "this should not be empty")
    private String pickupLocation;

    @NotEmpty(message = "this should not be empty")
    private String dropOffLocation;

    private int fare;
    private LocalDateTime bookingTime;
    private TaxiStatus status;
   }
