package com.example.taxibillingsystem.contract.response;

import com.example.taxibillingsystem.enums.TaxiStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
