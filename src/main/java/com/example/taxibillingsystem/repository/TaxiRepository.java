package com.example.taxibillingsystem.repository;

import com.example.taxibillingsystem.model.Taxi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxiRepository extends JpaRepository<Taxi, Long> {

       Taxi findByCurrentLocation(String pickupLocation);
}

