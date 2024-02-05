package com.example.taxibillingsystem.service;

import com.example.taxibillingsystem.contract.request.AccountBalanceRequest;
import com.example.taxibillingsystem.contract.request.LoginRequest;
import com.example.taxibillingsystem.contract.request.UserRequest;
import com.example.taxibillingsystem.contract.response.UserResponse;
import com.example.taxibillingsystem.exception.UserNotFoundException;
import com.example.taxibillingsystem.model.User;
import com.example.taxibillingsystem.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(ModelMapper modelMapper, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public UserResponse registerUser(UserRequest userRequest) {
        User user = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .accountBalance(0)
                .build();
        return modelMapper.map(user, UserResponse.class);
    }

    public String loginDetails(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
        if (user.isEmpty()) {
            throw new UserNotFoundException("Invalid User");
        } else {
            return "Successfully logged in";
        }
    }

    public UserResponse accountBalanceDetails(AccountBalanceRequest accountBalanceRequest, long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("Invalid User");
        }
        User updatedUser = User.builder()
                .userId(user.get().getUserId())
                .name(user.get().getName())
                .email(user.get().getEmail())
                .password(user.get().getPassword())
                .accountBalance(accountBalanceRequest.getAccountBalance())
                .build();
        User savedUser=userRepository.save(updatedUser);
        return modelMapper.map(savedUser, UserResponse.class);
    }
}
