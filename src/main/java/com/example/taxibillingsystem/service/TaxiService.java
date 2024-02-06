package com.example.taxibillingsystem.service;

import com.example.taxibillingsystem.contract.request.BookingRequest;
//import com.example.taxibillingsystem.contract.request.FareCalculationRequest;
import com.example.taxibillingsystem.contract.request.TaxiRequest;
import com.example.taxibillingsystem.contract.response.BookingResponse;
//import com.example.taxibillingsystem.contract.response.FareCalculationResponse;
import com.example.taxibillingsystem.contract.response.FareCalculationResponse;
import com.example.taxibillingsystem.contract.response.TaxiResponse;
import com.example.taxibillingsystem.exception.BookingNotFoundException;
import com.example.taxibillingsystem.exception.CancellationFailedException;
//import com.example.taxibillingsystem.exception.NoTaxiAvailableException;
import com.example.taxibillingsystem.exception.UserNotFoundException;
import com.example.taxibillingsystem.model.Booking;
import com.example.taxibillingsystem.model.Taxi;
import com.example.taxibillingsystem.model.TaxiStatus;
import com.example.taxibillingsystem.model.User;
import com.example.taxibillingsystem.repository.BookingRepository;
import com.example.taxibillingsystem.repository.TaxiRepository;
import com.example.taxibillingsystem.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaxiService {
    private final TaxiRepository taxiRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TaxiService(TaxiRepository taxiRepository, BookingRepository bookingRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.taxiRepository = taxiRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public TaxiResponse addATaxi(TaxiRequest taxiRequest) {
        Taxi taxi = Taxi.builder()
                .driversName(taxiRequest.getDriversName())
                .licenseNumber(taxiRequest.getLicenseNumber())
                .currentLocation(taxiRequest.getCurrentLocation())
                .build();
        Taxi savedTaxi = taxiRepository.save(taxi);
        return modelMapper.map(savedTaxi, TaxiResponse.class);
    }

    public BookingResponse bookATaxi(BookingRequest bookingRequest, long userId) {
        Optional<User> bookedUser = userRepository.findById(userId);
        if (bookedUser.isEmpty()) {
            throw new UserNotFoundException("User Not Found");
        }
        List<Taxi> listBookedTaxi = taxiRepository.findByCurrentLocation(bookingRequest.getPickupLocation());
        Taxi bookedTaxi = listBookedTaxi.get(0);
        TaxiStatus status = (bookedTaxi == null) ? TaxiStatus.WAITING : TaxiStatus.CONFIRMED;
        User user = bookedUser.get();
        Booking booking = Booking.builder()
                .userId(user)
                .taxiId(bookedTaxi)
                .pickupLocation(bookingRequest.getPickupLocation())
                .dropOffLocation(bookingRequest.getDropOffLocation())
                .bookingTime(LocalDateTime.now())
                .status(status)
                .build();
        Booking savedBooking = bookingRepository.save(booking);
        return modelMapper.map(savedBooking, BookingResponse.class);
    }

    public BookingResponse getBookingDetails(long bookingId) {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isEmpty()) {
            throw new BookingNotFoundException("Booking Not Found");
        }
        return modelMapper.map(optionalBooking, BookingResponse.class);
    }

    public String cancelABooking(long bookingId) {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isEmpty()) {
            throw new BookingNotFoundException("Booking Not Found");
        }
        bookingRepository.deleteById(bookingId);
        if (bookingRepository.existsById(bookingId)) {
            throw new CancellationFailedException("Cancellation Failure");
        } else {
            return "Successfully cancelled";
        }
    }

    public FareCalculationResponse calculateFare(Double distanceInKm, Double ratePerKm, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking Not Found"));

        Double fareAmount = distanceInKm * ratePerKm;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.format(fareAmount);
        Booking updatedBooking=Booking.builder()
                .bookingId(booking.getBookingId())
                .userId(booking.getUserId())
                .taxiId(booking.getTaxiId())
                .pickupLocation(booking.getPickupLocation())
                .dropOffLocation(booking.getDropOffLocation())
                .bookingTime(booking.getBookingTime())
                .fare(fareAmount)
                .status(booking.getStatus())
                .build();
        Booking savedBooking=bookingRepository.save(updatedBooking);
        return modelMapper.map(savedBooking,FareCalculationResponse.class);
    }
}

