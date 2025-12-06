package com.HulkHire_Tech.Payment;

import java.util.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpisApplication.class, args);
	}

}
