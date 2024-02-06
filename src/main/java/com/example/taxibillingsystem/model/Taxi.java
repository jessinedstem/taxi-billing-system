package com.example.taxibillingsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "taxis")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Taxi {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long taxiId;
    private String driversName;
    private int licenseNumber;
    private String currentLocation;
}

