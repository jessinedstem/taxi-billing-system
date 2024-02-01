package com.example.taxibillingsystem.repository;


import com.example.taxibillingsystem.contract.request.TaxiRequest;
import com.example.taxibillingsystem.model.Taxi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxiRepository extends JpaRepository<Integer, Long> {

    Taxi findByDriversName(TaxiRequest taxi);
}

