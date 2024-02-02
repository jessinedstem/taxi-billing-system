package com.example.taxibillingsystem.contract.request;

import com.example.taxibillingsystem.validation.ValidUserBalance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@ValidUserBalance
public class AmountBalanceRequest {

    private int accountBalance;
}
