package com.example.taxibillingsystem.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;


@Documented
@Constraint(validatedBy = BookingValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBooking {
    String message() default"Invalid Booking";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
