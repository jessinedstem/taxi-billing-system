package com.example.taxibillingsystem.contract.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaxiRequest {
    @NotEmpty(message = "Name should not be empty")
    private String driversName;

    @NotNull(message = "This should not be blank")
    private int licenseNumber;

    @NotNull(message = "This should not be blank")
    private String currentLocation;
}
