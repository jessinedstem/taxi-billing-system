package com.example.taxibillingsystem.service;

import com.example.taxibillingsystem.contract.request.BookingRequest;
import com.example.taxibillingsystem.contract.request.TaxiRequest;
import com.example.taxibillingsystem.contract.response.BookingResponse;
import com.example.taxibillingsystem.contract.response.TaxiResponse;
import com.example.taxibillingsystem.exception.BookingNotFoundException;
import com.example.taxibillingsystem.exception.CancellationFailedException;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaxiService {
    private final TaxiRepository taxiRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TaxiService(TaxiRepository taxiRepository, BookingRepository bookingRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.taxiRepository = taxiRepository;
        this.bookingRepository=bookingRepository;
        this.userRepository=userRepository;
        this.modelMapper = modelMapper;
    }
    public TaxiResponse addATaxi(TaxiRequest taxiRequest) {
        Taxi taxi=Taxi.builder()
                .driversName(taxiRequest.getDriversName())
                .licenseNumber(taxiRequest.getLicenseNumber())
                .currentLocation(taxiRequest.getCurrentLocation())
                .build();
        return modelMapper.map(taxi,TaxiResponse.class);
    }

    public BookingResponse bookATaxi(BookingRequest bookingRequest, long userId) {
        Optional<User> bookedUser = userRepository.findById(userId);
        if (bookedUser.isEmpty()) {
            throw new UserNotFoundException("User Not Found");
        }
        Taxi bookedTaxi = taxiRepository.findByCurrentLocation(bookingRequest.getPickupLocation());
        TaxiStatus status = (bookedTaxi == null) ? TaxiStatus.WAITING : TaxiStatus.CONFIRMED;
User user=bookedUser.get();
        Booking booking = Booking.builder()
                .userId(user)
                .taxiId(bookedTaxi)
                .pickupLocation(bookingRequest.getPickupLocation())
                .dropOffLocation(bookingRequest.getDropOffLocation())
                .bookingTime(LocalDateTime.now())
                .status(status)
                .build();
        Booking savedBooking=bookingRepository.save(booking);
        return modelMapper.map(savedBooking, BookingResponse.class);
    }

    public BookingResponse getBookingDetails(BookingRequest bookingRequest, long bookingId) {
        Optional<Booking> optionalBooking=bookingRepository.findById(bookingId);
        if(optionalBooking.isEmpty()){
            throw new BookingNotFoundException("Booking Not Found");
        }
        return modelMapper.map(optionalBooking, BookingResponse.class);
    }

    public String cancelABooking(long bookingId) {
        Optional<Booking> optionalBooking=bookingRepository.findById(bookingId);
        if(optionalBooking.isEmpty()){
            throw new BookingNotFoundException("Booking Not Found");
        }
        bookingRepository.deleteById(bookingId);
        if(bookingRepository.existsById(bookingId)){
            throw new CancellationFailedException("Cancellation Failure");
        }else {
            return "Successfully cancelled";
        }
    }
}

