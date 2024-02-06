package com.example.taxibillingsystem.service;

import com.example.taxibillingsystem.contract.request.AccountBalanceRequest;
import com.example.taxibillingsystem.contract.request.LoginRequest;
import com.example.taxibillingsystem.contract.request.UserRequest;
import com.example.taxibillingsystem.contract.response.LoginResponse;
import com.example.taxibillingsystem.contract.response.UserResponse;
import com.example.taxibillingsystem.model.User;
import com.example.taxibillingsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        userRepository = mock(UserRepository.class);
        modelMapper = mock(ModelMapper.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserService(modelMapper, userRepository, passwordEncoder);
    }
@Test
public void testRegisterUser(){
    UserRequest mockUserRequest=UserRequest.builder()
            .name("David")
            .email("devadiiii@gmail.com")
            .password("eeghbnfkk")
            .build();
    User mockUser = User.builder()
            .userId(1L)
            .name("Rajan")
            .email("rrr@gmail.com")
            .password("0909")
            .build();
    UserResponse userResponse = UserResponse.builder()
            .userId(1L)
            .name("Rajan")
            .email("rrr@gmail.com")
            .password("0909")
            .build();
    when(userRepository.save(any(User.class))).thenReturn(mockUser);
    when(modelMapper.map(mockUser, UserResponse.class)).thenReturn(userResponse);
    UserResponse actualResponse=userService.registerUser(mockUserRequest);
    assertNotNull(actualResponse);
    assertEquals("Rajan", actualResponse.getName());
    assertEquals("rrr@gmail.com", actualResponse.getEmail());
    assertEquals("0909", actualResponse.getPassword());
}
//@Test
//public void testLoginDetails(){
//    LoginRequest mockLoginRequest= LoginRequest.builder()
//            .email("gdhjskuk@gmail.com")
//            .password("jeevanam")
//            .build();
//    User mockUser = User.builder()
//            .userId(1L)
//            .name("Rajan")
//            .email("rrr@gmail.com")
//            .password("0909")
//            .build();
//    String loginResponse="Successfully logged in";
//    when(userRepository.save(any(User.class))).thenReturn(mockUser);
//    assertEquals("0909", actualResponse.getPassword());
//}
    @Test
    public void testAccountBalanceDetails(){
        AccountBalanceRequest amountBalanceRequest = AccountBalanceRequest.builder()
                .accountBalance(500)
                .build();
        User user = User.builder()
                .userId(1L)
                .name("Rajan")
                .email("rrr@gmail.com")
                .password("0909")
                .build();
        User updatedUser = User.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .accountBalance(user.getAccountBalance())
                .build();
        UserResponse userResponse = UserResponse.builder()
                .userId(1L)
                .name("Rajan")
                .email("rrr@gmail.com")
                .password("0909")
                .accountBalance(user.getAccountBalance())
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        when(modelMapper.map(updatedUser, UserResponse.class)).thenReturn(userResponse);

        UserResponse actualResponse = userService.accountBalanceDetails(amountBalanceRequest, 1L);

        assertNotNull(actualResponse);
        assertEquals("Rajan", actualResponse.getName());
        assertEquals("rrr@gmail.com", actualResponse.getEmail());
        assertEquals("0909", actualResponse.getPassword());
           }
}


