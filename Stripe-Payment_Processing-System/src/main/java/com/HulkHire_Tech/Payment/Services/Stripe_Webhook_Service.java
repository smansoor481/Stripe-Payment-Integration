package com.HulkHire_Tech.Payment.Services;

import org.springframework.stereotype.Service;

@Service
public interface Stripe_Webhook_Service {
    String handleStripeWebhook(String jsonPayload, String sigHeader);
}
