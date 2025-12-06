package com.HulkHire_Tech.PaymentProcessingService.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/health_check")
public class Health_Check {
    @GetMapping
    public String healthCheck() {
        log.info("Application is running");
        return "Payment Processing Service is up and running!";
    }
}
