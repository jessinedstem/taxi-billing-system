package com.example.taxibillingsystem.contract.response;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class TaxiInfoResponse {
    private long taxiId;
    private String driversName;
    private int licenseNumber;
}
