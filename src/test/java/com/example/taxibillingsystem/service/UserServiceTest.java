package com.example.taxibillingsystem.service;

import com.example.taxibillingsystem.TaxiBillingSystemApplication;
import com.example.taxibillingsystem.contract.request.AccountBalanceRequest;
import com.example.taxibillingsystem.contract.request.LoginRequest;
import com.example.taxibillingsystem.contract.request.UserRequest;
import com.example.taxibillingsystem.contract.response.UserResponse;
import com.example.taxibillingsystem.model.User;
import com.example.taxibillingsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes= TaxiBillingSystemApplication.class)
public class UserServiceTest {
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        modelMapper = mock(ModelMapper.class);
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);

        userService = new UserService(modelMapper, userRepository, passwordEncoder);
    }

    @Test
    void registerUser() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .name("Dennis")
                .email("dany4@gmail.com")
                .password("password123")
                .accountBalance(100)
                .build();
        User user = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password("encodedPassword")
                .build();
        UserResponse mockedUserResponse = UserResponse.builder()
                .userId(1)
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password("encodedPassword")
                .accountBalance(0)
                .build();
        when(passwordEncoder.encode(userRequest.getPassword())).thenReturn("encodedPassword");
        when(modelMapper.map(any(), any())).thenReturn(mockedUserResponse);
        when(userRepository.save(any(User.class))).thenReturn(user);
    }

    @Test
    public void logInDetails() throws Exception {
        LoginRequest mockLogInRequest = LoginRequest.builder()
                .password("eee")
                .email("see@gmail.com")
                .build();
        User mockUser = User.builder()
                .userId(1L)
                .name("Steve")
                .email("see@gmail.com")
                .password("see1123")
                .accountBalance(333)
                .build();
        when(userRepository.findByEmail("see@gmail.com")).thenReturn(mockUser);
        String result = userService.loginDetails(mockLogInRequest);
        assertEquals("Successfully logged in", result);
    }
    @Test
    public void testAccountBalanceDetails()throws Exception{
        AccountBalanceRequest mockAmountBalanceRequest= AccountBalanceRequest.builder()
                .accountBalance(12000)
                .build();
        User mockUser=User.builder()
                .userId(1L)
                .name("Jessin")
                .email("eee123@gmail.com")
                .password("ee123")
                .accountBalance(2500)
                .build();
        UserResponse userResponse=UserResponse.builder()
                .userId(1L)
                .name("Jessin")
                .email("eee123@gmail.com")
                .password("ee123")
                .accountBalance(2500)
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(userRepository.save(any(User.class))).thenReturn(mockUser);
        UserResponse response=userService.accountBalanceDetails(mockAmountBalanceRequest, mockUser.getUserId());
        assertEquals(mockUser.getUserId(), userResponse.getUserId());
        assertEquals(mockUser.getName(), userResponse.getName());
        assertEquals(mockUser.getEmail(), userResponse.getEmail());
        assertEquals(mockUser.getAccountBalance(), userResponse.getAccountBalance());
    }
}

