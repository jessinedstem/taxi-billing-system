package com.example.taxibillingsystem.contract.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserRequest {
    @NotBlank(message = "Name should not be blank")
    private String name;
    @NotBlank(message = "email should not be blank")
    private String email;
    @NotBlank(message = "password should not be blank")
    private String password;
    @NotEmpty(message = "AccountBalance should not be empty")
    private int accountBalance;
}
