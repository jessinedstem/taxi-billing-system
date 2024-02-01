package com.example.taxibillingsystem.contract.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaxiResponse {
    private long taxiId;
    private String driversName;
    private int licenseNumber;
    private String currentLocation;
    private List<BookingResponse> bookingId;
}
