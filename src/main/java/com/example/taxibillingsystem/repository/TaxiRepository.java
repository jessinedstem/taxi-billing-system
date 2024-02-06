package com.example.taxibillingsystem.repository;

import com.example.taxibillingsystem.model.Taxi;
import com.example.taxibillingsystem.model.TaxiStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxiRepository extends JpaRepository<Taxi, Long> {

       List<Taxi> findByCurrentLocation(String pickupLocation);

//       List<Taxi> findByStatus(TaxiStatus taxiStatus);
}

