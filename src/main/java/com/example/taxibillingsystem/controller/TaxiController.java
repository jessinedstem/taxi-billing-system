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

    @PostMapping("/bookATaxi")
    public ResponseEntity<BookingResponse> bookATaxi(@Valid @RequestBody BookingRequest bookingRequest) {
        {
            BookingResponse bookingResponse = taxiService.bookATaxi(bookingRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(bookingResponse);
        }

//    @GetMapping()
//    public ResponseEntity<List<TaxiResponse>> getAllBookingDetails(@RequestBody TaxiRequest taxiRequest) {
//        List<TaxiResponse> taxiResponseList = taxiService.getAllBookingDetails(taxiRequest);
//        return ResponseEntity.ok(taxiResponseList);
//    }
//
//    @DeleteMapping("/{taxiId}")
//    public ResponseEntity<TaxiResponse> cancelABooking(@PathVariable long taxiId) {
//        TaxiResponse responseCancel = taxiService.cancelABooking(taxiId);
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseCancel);
//}
    }
}