package com.example.taxibillingsystem.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public WebSecurityConfiguration(PasswordEncoder bCryptPasswordEncoder){
    this.bCryptPasswordEncoder=bCryptPasswordEncoder;
 }
}
