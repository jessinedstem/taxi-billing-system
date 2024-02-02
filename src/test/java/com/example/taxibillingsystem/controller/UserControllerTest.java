package com.example.taxibillingsystem.controller;

import com.example.taxibillingsystem.contract.request.AmountBalanceRequest;
import com.example.taxibillingsystem.contract.request.LoginRequest;
import com.example.taxibillingsystem.contract.request.UserRequest;
import com.example.taxibillingsystem.contract.response.UserResponse;
import com.example.taxibillingsystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean private UserService userService;

    @Test
    public void testRegisterUser() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .name("Dhruv")
                .email("dhruvan@gmail.com")
                .password("password123")
                .accountBalance(100)
                .build();

        UserResponse expectedUserResponse = UserResponse.builder()
                .userId(1)
                .name("Dhruv")
                .email("dhruvan@gmail.com")
                .accountBalance(100)
                .build();

        when(userService.registerUser(any(UserRequest.class))).thenReturn(expectedUserResponse);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Dhruv\",\"email\":\"dhruvan@gmail.com\",\"password\":\"password123\",\"accountBalance\":100}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testLoginDetails() throws Exception {
        LoginRequest mockRequest = LoginRequest.builder()
                .email("rajan.jessin@gmail.com")
                .password("eeee2345")
                .build();

        when(userService.loginDetails(any(LoginRequest.class))).thenReturn("Login successfully");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"rajan.jessin@gmail.com\",\"password\":\"eeee2345\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAccountBalanceDetails() throws Exception {
        AmountBalanceRequest amountBalanceRequest = AmountBalanceRequest.builder()
                .accountBalance(100L)
                .build();
        when(userService.accountBalanceDetails(any(AmountBalanceRequest.class), any(Long.class)))
                .thenReturn(UserResponse.builder()
                        .userId(1)
                        .name("jeena")
                        .email("jeenajohn@gmail.com")
                        .accountBalance(100)
                        .build());
        mockMvc.perform(post("/account-balance/{userId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountBalance\": 100}"))
                        .andExpect(status().isOk());
    }
}
