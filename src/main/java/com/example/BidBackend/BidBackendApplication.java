package com.example.BidBackend;

import com.example.BidBackend.repository.Part_EnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class BidBackendApplication  {
	@Autowired
	private Part_EnRepository partEnRepository;
	public static void main(String[] args) {
		SpringApplication.run(BidBackendApplication.class, args);
	}


}
