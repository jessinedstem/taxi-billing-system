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
    private String pickupLocation;
    private String dropOffLocation;
    private UserInfoResponse user;
    private TaxiInfoResponse taxi;
    private LocalDateTime bookingTime;
    private TaxiStatus status;

   }

