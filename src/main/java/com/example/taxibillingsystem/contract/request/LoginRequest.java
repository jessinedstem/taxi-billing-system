package com.example.taxibillingsystem.contract.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LoginRequest {
    @NotBlank(message = "Email should not be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password should not be blank")
    private String password;

}
