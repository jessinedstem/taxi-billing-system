package com.example.taxibillingsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.taxibillingsystem.TaxiBillingSystemApplication;
import com.example.taxibillingsystem.contract.request.BookingRequest;
import com.example.taxibillingsystem.contract.request.TaxiRequest;
import com.example.taxibillingsystem.contract.response.BookingResponse;
import com.example.taxibillingsystem.contract.response.FareCalculationResponse;
import com.example.taxibillingsystem.contract.response.TaxiInfoResponse;
import com.example.taxibillingsystem.contract.response.TaxiResponse;
import com.example.taxibillingsystem.contract.response.UserInfoResponse;
import com.example.taxibillingsystem.enums.TaxiStatus;
import com.example.taxibillingsystem.exception.BookingNotFoundException;
import com.example.taxibillingsystem.model.Booking;
import com.example.taxibillingsystem.model.Taxi;
import com.example.taxibillingsystem.model.User;
import com.example.taxibillingsystem.repository.BookingRepository;
import com.example.taxibillingsystem.repository.TaxiRepository;
import com.example.taxibillingsystem.repository.UserRepository;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TaxiBillingSystemApplication.class)
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
        taxiService =
                new TaxiService(taxiRepository, bookingRepository, userRepository, modelMapper);
    }

    @Test
    public void testAddATaxi() throws Exception {
        TaxiRequest mockTaxiRequest =
                TaxiRequest.builder()
                        .driversName("Jessin")
                        .licenseNumber(234)
                        .currentLocation("Mavelikkara")
                        .build();
        Taxi mockTaxi =
                Taxi.builder()
                        .taxiId(123L)
                        .driversName(mockTaxiRequest.getDriversName())
                        .licenseNumber(mockTaxiRequest.getLicenseNumber())
                        .currentLocation(mockTaxiRequest.getCurrentLocation())
                        .build();
        TaxiResponse mockTaxiResponse =
                TaxiResponse.builder()
                        .taxiId(mockTaxi.getTaxiId())
                        .driversName(mockTaxi.getDriversName())
                        .licenseNumber(mockTaxi.getLicenseNumber())
                        .currentLocation(mockTaxi.getCurrentLocation())
                        .build();
        when(taxiRepository.save(ArgumentMatchers.any(Taxi.class))).thenReturn(mockTaxi);
        when(modelMapper.map(any(Taxi.class), any())).thenReturn(mockTaxiResponse);
        TaxiResponse actualResponse = taxiService.addATaxi(mockTaxiRequest);
        assertNotNull(actualResponse);
        assertEquals(123L, actualResponse.getTaxiId());
        assertEquals("Jessin", actualResponse.getDriversName());
        assertEquals(234, actualResponse.getLicenseNumber());
        assertEquals("Mavelikkara", actualResponse.getCurrentLocation());
    }

    @Test
    public void testBookATaxi() {
        BookingRequest bookingRequest =
                BookingRequest.builder()
                        .pickupLocation("Tiruvalla")
                        .dropOffLocation("Kollam")
                        .build();
        Long userId = 1L;
        User bookedUser = User.builder().userId(1L).name("Kevin").email("kk@gmail.com").build();
        UserInfoResponse userInfoResponse =
                UserInfoResponse.builder()
                        .userId(bookedUser.getUserId())
                        .name(bookedUser.getName())
                        .email(bookedUser.getEmail())
                        .build();
        List<Taxi> bookedTaxiList = new ArrayList<>();
        Taxi bookedTaxi =
                Taxi.builder()
                        .taxiId(1L)
                        .driversName("Ravi")
                        .licenseNumber(88559)
                        .currentLocation("Kochi")
                        .build();
        bookedTaxiList.add(bookedTaxi);
        TaxiInfoResponse taxiInfoResponse =
                TaxiInfoResponse.builder()
                        .taxiId(bookedTaxi.getTaxiId())
                        .driversName(bookedTaxi.getDriversName())
                        .licenseNumber(bookedTaxi.getLicenseNumber())
                        .build();
        TaxiStatus status = TaxiStatus.CONFIRMED;
        Booking savedBooking =
                Booking.builder()
                        .userId(bookedUser)
                        .taxiId(bookedTaxi)
                        .pickupLocation(bookingRequest.getPickupLocation())
                        .dropOffLocation(bookingRequest.getDropOffLocation())
                        .bookingTime(LocalDateTime.now())
                        .status(status)
                        .build();
        BookingResponse bookingResponse =
                BookingResponse.builder()
                        .bookingId(1L)
                        .user(userInfoResponse)
                        .taxi(taxiInfoResponse)
                        .pickupLocation(bookingRequest.getPickupLocation())
                        .dropOffLocation(bookingRequest.getDropOffLocation())
                        .bookingTime(LocalDateTime.now())
                        .status(status)
                        .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(bookedUser));
        when(taxiRepository.findByCurrentLocation(bookingRequest.getPickupLocation()))
                .thenReturn(bookedTaxiList);
        when(bookingRepository.save(ArgumentMatchers.any(Booking.class))).thenReturn(savedBooking);
        when(modelMapper.map(savedBooking, BookingResponse.class)).thenReturn(bookingResponse);

        BookingResponse actualResponse = taxiService.bookATaxi(bookingRequest, userId);

        assertNotNull(actualResponse);
        assertEquals(1L, actualResponse.getBookingId());
        assertNotNull(actualResponse.getUser());
        assertNotNull(actualResponse.getTaxi());
        assertEquals("Tiruvalla", actualResponse.getPickupLocation());
        assertEquals("Kollam", actualResponse.getDropOffLocation());
        assertEquals(status, actualResponse.getStatus());
    }

    @Test
    public void testGetBookingDetails() throws Exception {
        BookingResponse expectedBookingResponse =
                BookingResponse.builder()
                        .bookingId(1L)
                        .user(UserInfoResponse.builder().userId(1L).build())
                        .taxi(TaxiInfoResponse.builder().taxiId(1L).build())
                        .pickupLocation("Thattarambalam")
                        .dropOffLocation("Mavelikkara")
                        .bookingTime(LocalDateTime.of(2022, 5, 20, 15, 20, 45, 30))
                        .status(TaxiStatus.CONFIRMED)
                        .build();
        Booking mockBooking =
                Booking.builder()
                        .bookingId(1L)
                        .userId(User.builder().userId(1L).build())
                        .taxiId(Taxi.builder().taxiId(1L).build())
                        .pickupLocation("Thattarambalam")
                        .dropOffLocation("Mavelikkara")
                        .bookingTime(LocalDateTime.of(2022, 5, 20, 15, 20, 45, 30))
                        .status(TaxiStatus.CONFIRMED)
                        .build();
        when(bookingRepository.findById(any())).thenReturn(Optional.of(mockBooking));
        when(modelMapper.map(any(), eq(BookingResponse.class))).thenReturn(expectedBookingResponse);
        when(taxiService.getBookingDetails(anyLong())).thenReturn(expectedBookingResponse);

        BookingResponse actualBookingResponse =
                taxiService.getBookingDetails(mockBooking.getBookingId());

        assertEquals(expectedBookingResponse.getBookingId(), actualBookingResponse.getBookingId());
        assertEquals(
                expectedBookingResponse.getPickupLocation(),
                actualBookingResponse.getPickupLocation());
        assertEquals(
                expectedBookingResponse.getDropOffLocation(),
                actualBookingResponse.getDropOffLocation());
    }

    @Test
    public void testCancelABooking() {
        Long bookingId = 1L;
        Booking booking =
                Booking.builder()
                        .bookingId(1L)
                        .pickupLocation("Pala")
                        .dropOffLocation("Kollam")
                        .bookingTime(LocalDateTime.now())
                        .status(TaxiStatus.CONFIRMED)
                        .build();
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        doNothing().when(bookingRepository).deleteById(bookingId);
        when(bookingRepository.existsById(bookingId)).thenReturn(false);
        String actualResult = taxiService.cancelABooking(bookingId);
        assertEquals("Successfully cancelled", actualResult);
    }

    @Test
    public void testCalculateFare() {
        Long bookingId = 1L;
        Double distanceInKm = 10.0;
        Double ratePerKm = 5.0;
        Double fareTotal = distanceInKm * ratePerKm;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.format(fareTotal);
        User mockUpdatedUser =
                User.builder()
                        .userId(1L)
                        .name("jessin")
                        .email("rajan.jessin@gmail.com")
                        .accountBalance(20000)
                        .build();
        Booking booking =
                Booking.builder()
                        .bookingId(1L)
                        .userId(mockUpdatedUser)
                        .pickupLocation("Tiruvalla")
                        .dropOffLocation("Kollam")
                        .bookingTime(LocalDateTime.now())
                        .status(TaxiStatus.CONFIRMED)
                        .build();
        Booking saved =
                Booking.builder()
                        .bookingId(booking.getBookingId())
                        .userId(mockUpdatedUser)
                        .pickupLocation(booking.getPickupLocation())
                        .dropOffLocation(booking.getDropOffLocation())
                        .bookingTime(booking.getBookingTime())
                        .status(booking.getStatus())
                        .fare(fareTotal)
                        .build();
        FareCalculationResponse fareCalculationResponse =
                FareCalculationResponse.builder()
                        .bookingId(saved.getBookingId())
                        .pickupLocation(saved.getPickupLocation())
                        .dropOffLocation(saved.getDropOffLocation())
                        .fare(saved.getFare())
                        .build();
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(ArgumentMatchers.any(Booking.class))).thenReturn(saved);
        when(modelMapper.map(saved, FareCalculationResponse.class))
                .thenReturn(fareCalculationResponse);
        when(userRepository.save(any(User.class))).thenReturn(mockUpdatedUser);
        when(userRepository.findById(booking.getUserId().getUserId()))
                .thenReturn(Optional.of(mockUpdatedUser));
        FareCalculationResponse actualResponse =
                taxiService.calculateFare(distanceInKm, ratePerKm, bookingId);
        assertNotNull(actualResponse);
        assertEquals(saved.getBookingId(), actualResponse.getBookingId());
        assertEquals("Tiruvalla", actualResponse.getPickupLocation());
        assertEquals("Kollam", actualResponse.getDropOffLocation());
        assertEquals(saved.getFare(), actualResponse.getFare());
    }

    @Test
    public void testGetBookingDetails_throwsException() {
        Long bookingId = 1L;
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());
        BookingNotFoundException exception =
                assertThrows(
                        BookingNotFoundException.class,
                        () -> {
                            taxiService.getBookingDetails(bookingId);
                        });
        assertEquals("Booking Not Found", exception.getMessage());
    }

    @Test
    public void testCancelABooking_throwsException() {
        Long bookingId = 1L;
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());
        BookingNotFoundException exception =
                assertThrows(
                        BookingNotFoundException.class,
                        () -> {
                            taxiService.cancelABooking(bookingId);
                        });
        assertEquals("Booking Not Found", exception.getMessage());
    }

    @Test
    public void testCalculateFare_BookingNotFound() {
        long bookingId = 1L;
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(
                BookingNotFoundException.class,
                () -> taxiService.calculateFare(10.0, 5.0, bookingId),
                "Expected BookingNotFoundException when booking is not found");
    }
}
