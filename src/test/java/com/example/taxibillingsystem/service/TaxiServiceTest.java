package com.example.taxibillingsystem.service;

import com.example.taxibillingsystem.TaxiBillingSystemApplication;
import com.example.taxibillingsystem.contract.request.BookingRequest;
import com.example.taxibillingsystem.contract.request.TaxiRequest;
import com.example.taxibillingsystem.contract.response.BookingResponse;
import com.example.taxibillingsystem.contract.response.TaxiResponse;
import com.example.taxibillingsystem.model.Booking;
import com.example.taxibillingsystem.model.Taxi;
import com.example.taxibillingsystem.model.TaxiStatus;
import com.example.taxibillingsystem.model.User;
import com.example.taxibillingsystem.repository.BookingRepository;
import com.example.taxibillingsystem.repository.TaxiRepository;
import com.example.taxibillingsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes=TaxiBillingSystemApplication.class)
public class TaxiServiceTest {
    private TaxiRepository taxiRepository;
    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private TaxiService taxiService;
    private ModelMapper modelMapper;


    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        bookingRepository = mock(BookingRepository.class);
        userRepository = mock(UserRepository.class);
        taxiRepository = mock(TaxiRepository.class);
        modelMapper = mock(ModelMapper.class);
        taxiService = new TaxiService(taxiRepository, bookingRepository, userRepository, modelMapper);
    }

    @Test
    public void testAddATaxi() throws Exception {
        TaxiRequest mockTaxiRequest = new TaxiRequest("Jessin", 234, "Mavelikkara");
        Taxi mockTaxi = new Taxi(123L, "Jessin", 234, "Mavelikkara");
        TaxiResponse mockTaxiResponse = new TaxiResponse(123L, "Jessin", 234, "Mavelikkara");
        when(modelMapper.map(any(Taxi.class), any())).thenReturn(mockTaxiResponse);
        TaxiResponse actualTaxiResponse = taxiService.addATaxi(mockTaxiRequest);
        assertEquals(mockTaxiResponse, actualTaxiResponse);
    }

    @Test
    public void testBookATaxi() throws Exception {
        BookingRequest mockBookingRequest = new BookingRequest("Thattarambalam", "Muttom");
        User mockBookedUser =new User(1L,"jeevan","jen2@gmail.com","ee34",2300L);
        Taxi mockBookedTaxi=new Taxi(1L,"jen",2349,"Karipuzha");
        Booking mockBookingResponse = Booking.builder()
                .bookingId(1L)
                .userId(User.builder().userId(1L).build())
                .taxiId(Taxi.builder().taxiId(1L).build())
                .pickupLocation("Thattarambalam")
                .dropOffLocation("Mavelikkara")
                .fare(200)
                .bookingTime(LocalDateTime.of(2022, 5, 20, 15, 20, 45, 30))
                .status(TaxiStatus.CONFIRMED)
                .build();
//        BookingResponse expectedBookingResponse=new BookingResponse(1L,"Karipuzha","Kandiyoor");
        when(userRepository.findById(mockBookedUser.getUserId())).thenReturn(Optional.of(mockBookedUser));
        when(taxiRepository.findByCurrentLocation(mockBookingRequest.getPickupLocation())).thenReturn(mockBookedTaxi);
//        when(modelMapper.map(any(Booking.class),any())).thenReturn(mockBookingResponse);
        System.out.println(mockBookingRequest);
        System.out.println(mockBookedUser.getUserId());
        BookingResponse actualBookingResponse=taxiService.bookATaxi(mockBookingRequest, mockBookedUser.getUserId());
        System.out.println(actualBookingResponse);
        assertEquals(mockBookingResponse,actualBookingResponse);
    }
@Test
public void testGetBookingDetails() throws Exception {
    BookingRequest mockBookingRequest = new BookingRequest("Muttom", "Kavala");
    BookingResponse expectedBookingResponse = new BookingResponse(1L, "Kandiyoor", "Mavelikkara");
    Booking mockBooking = Booking.builder()
            .bookingId(1L)
            .userId(User.builder().userId(1L).build())
            .taxiId(Taxi.builder().taxiId(1L).build())
            .pickupLocation("Thattarambalam")
            .dropOffLocation("Mavelikkara")
            .fare(200)
            .bookingTime(LocalDateTime.of(2022, 5, 20, 15, 20, 45, 30))
            .status(TaxiStatus.CONFIRMED)
            .build();
    when(bookingRepository.findById(any())).thenReturn(Optional.of(mockBooking));
    when(modelMapper.map(any(), eq(BookingResponse.class))).thenReturn(expectedBookingResponse);
    when(taxiService.getBookingDetails(any(BookingRequest.class), anyLong()))
            .thenReturn(expectedBookingResponse);

    BookingResponse actualBookingResponse=taxiService.getBookingDetails(mockBookingRequest, mockBooking.getBookingId());

    assertEquals(expectedBookingResponse.getBookingId(), actualBookingResponse.getBookingId());
    assertEquals(expectedBookingResponse.getPickupLocation(), actualBookingResponse.getPickupLocation());
    assertEquals(expectedBookingResponse.getDropOffLocation(), actualBookingResponse.getDropOffLocation());

    }
//@Test
//    public void testCancelABooking()throws Exception{
//    long bookingId = 1L;
//
//
//}

}

