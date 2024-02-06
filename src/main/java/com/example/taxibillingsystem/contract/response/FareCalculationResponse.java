package com.example.taxibillingsystem.contract.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FareCalculationResponse {
    private long bookingId;
    private String pickupLocation;
    private String dropOffLocation;
    private double fare;
}
