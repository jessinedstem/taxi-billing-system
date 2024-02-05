package com.example.taxibillingsystem.controller;

import com.example.taxibillingsystem.contract.request.BookingRequest;
import com.example.taxibillingsystem.contract.request.TaxiRequest;
import com.example.taxibillingsystem.contract.response.BookingResponse;
import com.example.taxibillingsystem.contract.response.TaxiResponse;
import com.example.taxibillingsystem.service.TaxiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
public class TaxiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaxiService taxiService;
    @Test
    public void addATaxi() throws Exception{
        TaxiResponse mockTaxiResponse= TaxiResponse.builder()
                .driversName("Jeevan")
                .licenseNumber(23456990)
                .currentLocation("Sreekaryam")
                .build();
        TaxiRequest taxiRequest= TaxiRequest.builder()
                .driversName("Jeevan")
                .licenseNumber(23456990)
                .currentLocation("Sreekaryam")
                .build();
        when(taxiService.addATaxi(taxiRequest)).thenReturn(mockTaxiResponse);
        mockMvc.perform(post("/taxi/addATaxi")
                        .contentType(MediaType.APPLICATION_JSON)

                .content(new ObjectMapper().writeValueAsString(taxiRequest)))
                .andExpect(status().isCreated());
    }
    @Test
    public void testBookATaxi() throws Exception {
        BookingRequest bookingRequest = BookingRequest.builder()
                .pickupLocation("Muttom")
                .dropOffLocation("Haripad")
                .build();
        long userId=1L;
        when(taxiService.bookATaxi(any(BookingRequest.class), any(Long.class)))
                .thenReturn(BookingResponse.builder()
                        .bookingId(1)
                        .pickupLocation("Muttom")
                        .dropOffLocation("Haripad")
                        .build());
        mockMvc.perform(post("/taxi/bookATaxi/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookingRequest)))
                        .andExpect(status().isCreated());
    }
    @Test
    public void testGetBookingDetails() throws Exception {
        BookingRequest bookingRequest = BookingRequest.builder()
                .pickupLocation("Muttom")
                .dropOffLocation("Haripad")
                .build();
        Long bookingId=1L;
        when(taxiService.getBookingDetails(any(BookingRequest.class), any(Long.class)))
                .thenReturn(BookingResponse.builder()
                        .bookingId(1)
                        .pickupLocation("Muttom")
                        .dropOffLocation("Haripad")
                        .build());
        mockMvc.perform(get("/taxi/booking-details/{bookingId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookingRequest)))
                        .andExpect(status().isOk());
    }
    @Test
    public void testCancelABooking() throws Exception{
        long bookingId=1L;
        String response="Successfully Cancelled";
        when(taxiService.cancelABooking(bookingId)).thenReturn(response);
        mockMvc.perform(delete("/taxi/cancel/{bookingId}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(response)))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully Cancelled"));
    }
}
