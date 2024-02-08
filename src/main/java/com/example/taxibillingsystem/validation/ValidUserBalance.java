package com.example.taxibillingsystem.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserBalanceValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUserBalance {
    String message() default "Invalid account balance";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
