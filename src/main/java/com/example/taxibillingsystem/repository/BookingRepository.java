package com.example.taxibillingsystem.repository;

import com.example.taxibillingsystem.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
}
