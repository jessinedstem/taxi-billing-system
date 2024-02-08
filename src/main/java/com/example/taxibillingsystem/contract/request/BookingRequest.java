package com.example.taxibillingsystem.contract.request;

import com.example.taxibillingsystem.validation.ValidBooking;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Builder
@Setter
@ValidBooking
public class BookingRequest {

    @NotEmpty(message = "this should not be empty")
    private String pickupLocation;

    @NotEmpty(message = "this should not be empty")
    private String dropOffLocation;
}
