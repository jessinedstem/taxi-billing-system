package com.example.taxibillingsystem.contract.request;

import com.example.taxibillingsystem.validation.ValidUserBalance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ValidUserBalance
public class AccountBalanceRequest {

    private long accountBalance;
}
