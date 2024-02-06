package com.example.taxibillingsystem.controller;

import com.example.taxibillingsystem.contract.request.AccountBalanceRequest;
import com.example.taxibillingsystem.contract.request.LoginRequest;
import com.example.taxibillingsystem.contract.request.UserRequest;
import com.example.taxibillingsystem.contract.response.LoginResponse;
import com.example.taxibillingsystem.contract.response.UserResponse;
import com.example.taxibillingsystem.exception.UserNotFoundException;
import com.example.taxibillingsystem.service.JwtService;
import com.example.taxibillingsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserService userService,AuthenticationManager authenticationManager,JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager=authenticationManager;
        this.jwtService=jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest userRequest){
        UserResponse userResponse=userService.registerUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginDetails(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            LoginResponse response = jwtService.generateToken(loginRequest.getEmail());
            return ResponseEntity.ok(response);
        } else {
            throw new UserNotFoundException("Invalid user request !");
        }
    }
    @PostMapping("/account-balance/{userId}")
    public ResponseEntity<UserResponse> accountBalance(@Valid @RequestBody AccountBalanceRequest accountBalanceRequest,
                                                       @PathVariable long userId){
        UserResponse response=userService.accountBalanceDetails(accountBalanceRequest,userId);
        return ResponseEntity.ok(response);
    }
}
