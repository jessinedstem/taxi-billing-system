package com.example.taxibillingsystem.controller;

import com.example.taxibillingsystem.contract.request.BookingRequest;
import com.example.taxibillingsystem.contract.request.TaxiRequest;
import com.example.taxibillingsystem.contract.response.BookingResponse;
import com.example.taxibillingsystem.contract.response.TaxiResponse;
import com.example.taxibillingsystem.service.TaxiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaxiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaxiService taxiService;
    @Test
    public void addATaxi(){
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
        mockMvc.perform(post("addATaxi"))
                .andExpect(status().isOk());
    }
    @Test
    public void testBookATaxi() throws Exception {
        BookingRequest bookingRequest = BookingRequest.builder()
                .pickupLocation("Muttom")
                .dropOffLocation("Haripad")
                .build();
        when(taxiService.bookATaxi(any(BookingRequest.class), any(Long.class)))
                .thenReturn(BookingResponse.builder()
                        .bookingId(1)
                        .pickupLocation("Muttom")
                        .dropOffLocation("Haripad")
                        .build());
        mockMvc.perform(post("/bookATaxi/{userId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"pickupLocation\":\"Muttom\",\"dropOffLocation\":\"Haripad\"}"))
                .andExpect(status().isCreated());
    }
    @Test
    public void testGetBookingDetails() throws Exception {
        BookingRequest bookingRequest = BookingRequest.builder()
                .pickupLocation("Muttom")
                .dropOffLocation("Haripad")
                .build();
        when(taxiService.getBookingDetails(any(BookingRequest.class), any(Long.class)))
                .thenReturn(BookingResponse.builder()
                        .bookingId(1)
                        .pickupLocation("Muttom")
                        .dropOffLocation("Haripad")
                        .build());
        mockMvc.perform(get("/booking-details/{bookingId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"pickupLocation\":\"Muttom\",\"dropOffLocation\":\"Haripad\"}"))
                        .andExpect(status().isOk());
    }
}
