package com.example.taxibillingsystem.controller;

import com.example.taxibillingsystem.contract.request.BookingRequest;
import com.example.taxibillingsystem.contract.request.TaxiRequest;
import com.example.taxibillingsystem.contract.response.BookingResponse;
import com.example.taxibillingsystem.contract.response.TaxiResponse;
import com.example.taxibillingsystem.service.TaxiService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<BookingResponse> bookATaxi(@Valid @RequestBody BookingRequest bookingRequest,
                                                     @PathVariable long userId) {
            BookingResponse bookingResponse = taxiService.bookATaxi(bookingRequest,userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(bookingResponse);
    }

    @GetMapping("/booking-details/{bookingId}")
    public ResponseEntity<BookingResponse> getBookingDetails(@RequestBody BookingRequest bookingRequest,
                                                             @PathVariable long bookingId) {
        BookingResponse response= taxiService.getBookingDetails(bookingRequest,bookingId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/cancel/{bookingId}")
    public ResponseEntity<Void> cancelABooking(@PathVariable long bookingId) {
        String responseCancel = taxiService.cancelABooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}