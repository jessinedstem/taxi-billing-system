package com.example.taxibillingsystem.contract.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
