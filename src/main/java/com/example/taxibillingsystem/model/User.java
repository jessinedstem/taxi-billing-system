package com.example.taxibillingsystem.model;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "User-cab")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long userId;
    private String name;
    private String email;
    private String password;
    private int accountBalance;
}

