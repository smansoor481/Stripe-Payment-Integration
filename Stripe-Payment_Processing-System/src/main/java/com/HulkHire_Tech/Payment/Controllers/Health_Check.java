package com.HulkHire_Tech.Payment.Controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class Health_Check {

    @GetMapping("/health_check")
    public String healthCheck() {
        log.info("=> Health_Check Api Hit");
        return "Payment Service is up and running!";
    }
}