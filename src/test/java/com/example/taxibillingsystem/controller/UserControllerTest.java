package com.example.taxibillingsystem.controller;

import com.example.taxibillingsystem.contract.request.AccountBalanceRequest;
import com.example.taxibillingsystem.contract.request.LoginRequest;
import com.example.taxibillingsystem.contract.request.UserRequest;
import com.example.taxibillingsystem.contract.response.LoginResponse;
import com.example.taxibillingsystem.contract.response.UserResponse;
import com.example.taxibillingsystem.service.JwtService;
import com.example.taxibillingsystem.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    public void testRegisterUser() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .name("Dhruv")
                .email("dhruvan@gmail.com")
                .password("password123")
                .build();
        UserResponse expectedUserResponse = UserResponse.builder()
                .userId(1L)
                .name("Dhruv")
                .email("dhruvan@gmail.com")
                .password("password123")
                .build();

        when(userService.registerUser(any(UserRequest.class))).thenReturn(expectedUserResponse);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userRequest)))
                .andExpect(status().isCreated());

    }

// @Test
//    public void testLoginDetails_ValidCredentials() throws Exception {
//     String email = "test@example.com";
//     String password = "password123";
//     when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(
//             new UsernamePasswordAuthenticationToken(email, password)
//     );
//     String token = "token-value";
//     when(jwtService.generateToken(email)).thenReturn(new LoginResponse(email, token));
//     LoginRequest loginRequest = new LoginRequest(email, password);
//
//     mockMvc.perform(post("/login")
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .content(new ObjectMapper().writeValueAsString(loginRequest)))
//             .andExpect(status().isOk());
//
// }
 @Test
    public void testAccountBalance() throws Exception {
        AccountBalanceRequest accountBalanceRequest = AccountBalanceRequest.builder()
                .accountBalance(100L)
                .build();
        when(userService.accountBalanceDetails(any(AccountBalanceRequest.class), any(Long.class)))
                .thenReturn(UserResponse.builder()
                        .userId(1L)
                        .name("jeena")
                        .email("jeenajohn@gmail.com")
                        .accountBalance(100)
                        .build());
        mockMvc.perform(post("/users/account-balance/"+ 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(accountBalanceRequest)))
                .andExpect(status().isOk());
    }
}

