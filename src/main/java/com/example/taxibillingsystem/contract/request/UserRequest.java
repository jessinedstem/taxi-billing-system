package com.example.taxibillingsystem.contract.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "password should not be blank")
    @Size(min = 8, message = "Password should have at least 8 characters")
    private String password;
}
