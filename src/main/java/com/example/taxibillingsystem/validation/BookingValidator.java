package com.example.taxibillingsystem.validation;

import com.example.taxibillingsystem.contract.request.BookingRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BookingValidator implements ConstraintValidator<ValidBooking, BookingRequest> {

    @Override
    public boolean isValid(
            BookingRequest bookingRequest, ConstraintValidatorContext constraintValidatorContext) {
        if (bookingRequest == null) {
            return true;
        } else {
            return (!bookingRequest
                    .getPickupLocation()
                    .equals(bookingRequest.getDropOffLocation()));
        }
    }
}
