package com.example.taxibillingsystem;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TaxiBillingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxiBillingSystemApplication.class, args);
	}

	@Bean
	public ModelMapper mapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
				.setFieldMatchingEnabled(true)
				.setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
		return mapper;
	}
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
