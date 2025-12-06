package com.HulkHire_Tech.Payment.Controllers;

import com.HulkHire_Tech.Payment.Services.Stripe_Webhook_Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/stripe/webhook")
@RequiredArgsConstructor
public class Stripe_Webhook_Controller {

    private final Stripe_Webhook_Service stripe_webhook_service;

    @PostMapping
    public String handleStripeWebhook(
            @RequestHeader("Stripe-Signature")String sigHeader,
            @RequestBody String jsonPayload) {
        // Implement webhook handling logic here
        log.info("*=> Received Stripe webhook event by Stripe CLI");

        String Response = stripe_webhook_service.handleStripeWebhook(jsonPayload, sigHeader);

        return "Webhook Controller is working fine";
    }
}
// stripe listen --forward-to localhost:8083/v1/stripe/webhook
