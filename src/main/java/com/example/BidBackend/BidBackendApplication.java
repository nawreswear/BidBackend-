package com.example.BidBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class BidBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(BidBackendApplication.class, args);
	}

}
