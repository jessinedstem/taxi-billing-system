package com.example.taxibillingsystem.controller;

import com.example.taxibillingsystem.contract.request.BookingRequest;
import com.example.taxibillingsystem.contract.request.TaxiRequest;
import com.example.taxibillingsystem.contract.response.*;
import com.example.taxibillingsystem.model.Taxi;
import com.example.taxibillingsystem.model.TaxiStatus;
import com.example.taxibillingsystem.service.TaxiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.param;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class TaxiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaxiService taxiService;
   @Test
   public void addATaxi() throws Exception{
        TaxiResponse mockTaxiResponse= TaxiResponse.builder()
                .taxiId(1L)
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
                        .user(UserInfoResponse.builder()
                                .userId(1L)
                                .name("see")
                                .email("seeeeee@gmail.com")
                                .build())
                        .taxi(TaxiInfoResponse.builder()
                                .taxiId(1L)
                                .driversName("jayan")
                                .licenseNumber(123457855)
                                .build())
                        .bookingTime(LocalDateTime.of(2024,02,05,13,24,56))
                        .status(TaxiStatus.WAITING)
                        .build());
        mockMvc.perform(post("/taxi/bookATaxi/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookingRequest)))
                        .andExpect(status().isCreated());
    }
    @Test
    public void testGetBookingDetails() throws Exception {

        Long bookingId=1L;
        when(taxiService.getBookingDetails(any(Long.class)))
                .thenReturn(BookingResponse.builder()
                        .bookingId(1)
                        .pickupLocation("Muttom")
                        .dropOffLocation("Haripad")
                        .build());
        mockMvc.perform(get("/taxi/booking-details/{bookingId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookingId)))
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
    @Test
    public void testCalculateFare() throws Exception {
        Long bookingId = 1L;
        double distanceInKm = 10.0;
        double ratePerKm = 5.0;
        FareCalculationResponse mockCalculationResponse = FareCalculationResponse.builder()
                .bookingId(1L)
                .pickupLocation("Karunagappally")
                .dropOffLocation("Kattanam")
                .fare(50)
                .build();
        when(taxiService.calculateFare(distanceInKm, ratePerKm, bookingId))
                .thenReturn(mockCalculationResponse);
        mockMvc.perform(post("/taxi/calculate-fare/" + bookingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("distanceInKm", String.valueOf(distanceInKm))
                        .param("ratePerKm", String.valueOf(ratePerKm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingId").value(bookingId))
                .andExpect(jsonPath("$.pickupLocation").value("Karunagappally"))
                .andExpect(jsonPath("$.dropOffLocation").value("Kattanam"))
                .andExpect(jsonPath("$.fare").value(50.0));
    }
}
