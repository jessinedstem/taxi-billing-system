package com.example.taxibillingsystem.service;

import com.example.taxibillingsystem.contract.request.AccountBalanceRequest;
import com.example.taxibillingsystem.contract.request.UserRequest;
import com.example.taxibillingsystem.contract.response.UserResponse;
import com.example.taxibillingsystem.exception.UserNotFoundException;
import com.example.taxibillingsystem.model.User;
import com.example.taxibillingsystem.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(
            ModelMapper modelMapper,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse registerUser(UserRequest userRequest) {
        User user =
                User.builder()
                        .name(userRequest.getName())
                        .email(userRequest.getEmail())
                        .password(passwordEncoder.encode(userRequest.getPassword()))
                        .accountBalance(0)
                        .build();
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserResponse.class);
    }

    public UserResponse accountBalanceDetails(
            AccountBalanceRequest accountBalanceRequest, long userId) {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        User updatedUser =
                User.builder()
                        .userId(user.getUserId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .accountBalance(accountBalanceRequest.getAccountBalance())
                        .build();
        User saved = userRepository.save(updatedUser);
        return modelMapper.map(saved, UserResponse.class);
    }
}
