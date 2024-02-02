package com.example.taxibillingsystem.validation;

import com.example.taxibillingsystem.contract.request.UserRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserBalanceValidator implements ConstraintValidator<ValidUserBalance, UserRequest> {

    public static final int MINIMUM_BALANCE = 0;
    @Override
    public void initialize(ValidUserBalance constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserRequest userRequest, ConstraintValidatorContext context) {
        if (userRequest == null) {
            return true;
        }
        return userRequest.getAccountBalance() >= MINIMUM_BALANCE;
    }
}

