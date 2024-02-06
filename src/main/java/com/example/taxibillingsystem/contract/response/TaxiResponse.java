package com.example.taxibillingsystem.contract.response;

import lombok.*;

import java.util.List;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class TaxiResponse {
    private long taxiId;
    private String driversName;
    private int licenseNumber;
    private String currentLocation;
   }
