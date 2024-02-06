package com.example.taxibillingsystem.validation;

import com.example.taxibillingsystem.contract.request.AccountBalanceRequest;
import com.example.taxibillingsystem.contract.request.UserRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserBalanceValidator implements ConstraintValidator<ValidUserBalance, AccountBalanceRequest> {

    public static final int MINIMUM_BALANCE = 0;
    @Override
    public void initialize(ValidUserBalance constraintAnnotation) {
    }

    @Override
    public boolean isValid(AccountBalanceRequest amountBalanceRequest, ConstraintValidatorContext context) {
        if (amountBalanceRequest == null) {
            return true;
        }
        return amountBalanceRequest.getAccountBalance() >= MINIMUM_BALANCE;
    }
}

