package com.example.taxibillingsystem.service;


import com.example.taxibillingsystem.contract.request.BookingRequest;
import com.example.taxibillingsystem.contract.request.TaxiRequest;
import com.example.taxibillingsystem.contract.response.BookingResponse;
import com.example.taxibillingsystem.contract.response.TaxiResponse;
import com.example.taxibillingsystem.model.Booking;
import com.example.taxibillingsystem.model.Taxi;
import com.example.taxibillingsystem.model.User;
import com.example.taxibillingsystem.repository.BookingRepository;
import com.example.taxibillingsystem.repository.TaxiRepository;
import com.example.taxibillingsystem.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        this.modelMapper = modelMapper;
        this.bookingRepository=bookingRepository;
        this.userRepository=userRepository;
    }
    public TaxiResponse addATaxi(TaxiRequest taxiRequest) {
        Taxi taxi=Taxi.builder()
                .driversName(taxiRequest.getDriversName())
                .licenseNumber(taxiRequest.getLicenseNumber())
                .currentLocation(taxiRequest.getCurrentLocation())
                .build();
        return modelMapper.map(taxi,TaxiResponse.class);
    }

    public BookingResponse bookATaxi(BookingRequest bookingRequest) {
        User bookedUser=userRepository.findByName(bookingRequest.getUser().getName());
        Taxi bookedTaxi=taxiRepository.findByDriversName(bookingRequest.getTaxi());
        Booking booking= Booking.builder()
                .userId(bookedUser)
                .taxiId(bookedTaxi)
                .pickupLocation(bookingRequest.getPickupLocation())
                .dropOffLocation(bookingRequest.getDropOffLocation())
                .fare(bookingRequest.getFare())
                .bookingTime(bookingRequest.getBookingTime())
                .status(bookingRequest.getStatus())
                .build();
        return modelMapper.map(booking,BookingResponse.class);
    }

//    public List<TaxiResponse> getAllBookingDetails(TaxiRequest taxiRequest) {
//        List<Taxi> taxiResponses1 = taxiRepository.findAllBooking(taxiRequest);
//        return taxiResponses1.stream().map(taxi -> modelMapper.map(taxi, TaxiResponse.class))
//                .collect(Collectors.toList());
//    }
//
//    public TaxiResponse cancelABooking(long taxiId) {
//
//    }
//

}

