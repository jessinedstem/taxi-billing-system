package com.example.taxibillingsystem.contract.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class BookingRequest {

    @NotEmpty(message = "this should not be empty")
    private String pickupLocation;

    @NotEmpty(message = "this should not be empty")
    private String dropOffLocation;

       }
