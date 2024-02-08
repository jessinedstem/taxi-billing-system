package com.example.taxibillingsystem.service;

import com.example.taxibillingsystem.exception.UserNotFoundException;
import com.example.taxibillingsystem.model.User;
import com.example.taxibillingsystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {
    @Autowired private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(username);
        return userOptional
                .map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
