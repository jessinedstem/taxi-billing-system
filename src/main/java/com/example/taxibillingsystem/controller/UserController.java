package com.example.taxibillingsystem.controller;

import com.example.taxibillingsystem.contract.request.AmountBalanceRequest;
import com.example.taxibillingsystem.contract.request.LoginRequest;
import com.example.taxibillingsystem.contract.request.UserRequest;
import com.example.taxibillingsystem.contract.response.UserResponse;
import com.example.taxibillingsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest userRequest){
        UserResponse userResponse=userService.registerUser(userRequest);
        return ResponseEntity.ok(userResponse);
    }
    @PostMapping("/login")
    public ResponseEntity<String> loginDetails(@Valid @RequestBody LoginRequest loginRequest){
        String response=userService.loginDetails(loginRequest);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/account-balance/{userId}")
    public ResponseEntity<UserResponse> accountBalance(@Valid @RequestBody AmountBalanceRequest amountBalanceRequest,
                                                       @PathVariable long userId){
        UserResponse response=userService.accountBalanceDetails(amountBalanceRequest,userId);
        return ResponseEntity.ok(response);
    }
}
