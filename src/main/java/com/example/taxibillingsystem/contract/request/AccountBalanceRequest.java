package com.example.taxibillingsystem.contract.request;

import com.example.taxibillingsystem.validation.ValidUserBalance;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ValidUserBalance
@Setter
public class AccountBalanceRequest {

    private long accountBalance;
}
