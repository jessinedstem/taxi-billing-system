package com.example.taxibillingsystem.contract.request;

import com.example.taxibillingsystem.validation.ValidUserBalance;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@ValidUserBalance
public class AccountBalanceRequest {
    @NotEmpty(message = "AccountBalance should not be empty")
    private long accountBalance;
}
