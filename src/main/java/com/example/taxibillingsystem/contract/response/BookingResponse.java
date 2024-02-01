package com.example.taxibillingsystem.contract.response;

import com.example.taxibillingsystem.contract.request.TaxiRequest;
import com.example.taxibillingsystem.contract.request.UserRequest;
import com.example.taxibillingsystem.model.TaxiStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponse {
    private long bookingId;
    private UserResponse user;
    private TaxiResponse taxi;
    @NotEmpty(message = "this should not be empty")
    private String pickupLocation;

    @NotEmpty(message = "this should not be empty")
    private String dropOffLocation;

    private long fare;
    private LocalDateTime bookingTime;
    private TaxiStatus status;
}

