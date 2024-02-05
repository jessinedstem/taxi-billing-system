package com.example.taxibillingsystem.controller;

import com.example.taxibillingsystem.contract.request.AccountBalanceRequest;
import com.example.taxibillingsystem.contract.request.LoginRequest;
import com.example.taxibillingsystem.contract.request.UserRequest;
import com.example.taxibillingsystem.contract.response.UserResponse;
import com.example.taxibillingsystem.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    public void testRegisterUser() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .name("Dhruv")
                .email("dhruvan@gmail.com")
                .password("password123")
                .accountBalance(0)
                .build();
        UserResponse expectedUserResponse = UserResponse.builder()
                .userId(1L)
                .name("Dhruv")
                .email("dhruvan@gmail.com")
                .password("password123")
                .accountBalance(0)
                .build();

        when(userService.registerUser(any(UserRequest.class))).thenReturn(expectedUserResponse);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userRequest)))
                .andExpect(status().isCreated());

    }

    @Test
    public void testLoginDetails() throws Exception {
        LoginRequest mockRequest = LoginRequest.builder()
                .email("rajan.jessin@gmail.com")
                .password("eeee2345")
                .build();


        when(userService.loginDetails(mockRequest)).thenReturn("Successfully logged in");
        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockRequest)))
                .andExpect(status().isOk());

    }

    @Test
    public void testAccountBalanceDetails() throws Exception {
        AccountBalanceRequest accountBalanceRequest = AccountBalanceRequest.builder()
                .accountBalance(100)
                .build();
        long userId = 1L;

        mockMvc.perform(put("/users/account-balance/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(accountBalanceRequest)))
                .andExpect(status().isOk());
    }
}
