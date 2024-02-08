package com.example.taxibillingsystem.controller;

import com.example.taxibillingsystem.contract.request.BookingRequest;
import com.example.taxibillingsystem.contract.request.TaxiRequest;
import com.example.taxibillingsystem.contract.response.BookingResponse;
import com.example.taxibillingsystem.contract.response.FareCalculationResponse;
import com.example.taxibillingsystem.contract.response.TaxiResponse;
import com.example.taxibillingsystem.service.TaxiService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/taxi")
public class TaxiController {

    private final TaxiService taxiService;

    @Autowired
    public TaxiController(TaxiService taxiService) {
        this.taxiService = taxiService;
    }

    @PostMapping("/addATaxi")
    public ResponseEntity<TaxiResponse> addATaxi(@Valid @RequestBody TaxiRequest taxiRequest) {
        TaxiResponse response = taxiService.addATaxi(taxiRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/bookATaxi/{userId}")
    public ResponseEntity<BookingResponse> bookATaxi(
            @Valid @RequestBody BookingRequest bookingRequest, @PathVariable long userId) {
        BookingResponse bookingResponse = taxiService.bookATaxi(bookingRequest, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingResponse);
    }

    @GetMapping("/booking-details/{bookingId}")
    public ResponseEntity<BookingResponse> getBookingDetails(@PathVariable long bookingId) {
        BookingResponse response = taxiService.getBookingDetails(bookingId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/cancel/{bookingId}")
    public ResponseEntity<String> cancelABooking(@PathVariable long bookingId) {
        String responseCancel = taxiService.cancelABooking(bookingId);
        return ResponseEntity.ok(responseCancel);
    }

    @PostMapping("/calculate-fare/{bookingId}")
    public ResponseEntity<FareCalculationResponse> calculateFare(
            @RequestParam Double distanceInKm,
            @RequestParam Double ratePerKm,
            @PathVariable Long bookingId) {
        FareCalculationResponse response =
                taxiService.calculateFare(distanceInKm, ratePerKm, bookingId);
        return ResponseEntity.ok(response);
    }
}
