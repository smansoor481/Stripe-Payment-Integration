package com.HulkHire_Tech.Payment.Services;

import com.HulkHire_Tech.Payment.Constants.ErrorCode;
import com.HulkHire_Tech.Payment.Pojo.Error_Response;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Stripe_Webhook_Service_impl implements Stripe_Webhook_Service {

    private final ProcessStripeEventAsync processStripeEventAsync;

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    @Override
    public String handleStripeWebhook(String jsonPayload, String sigHeader) {

        Event event = CheckWebhookSignatureValidation(jsonPayload, sigHeader);
        log.info("=> HmacSHA256 signature is valid.");

        processStripeEventAsync.ProcessingResponceEvent(event);
        log.info("=> Event processed successfully.");


        return "Webhook Service is working fine";
    }

    //This method Return valid event Object if signature is valid otherwise throw exception
    private Event CheckWebhookSignatureValidation(String jsonPayload, String sigHeader) {
        try {
            Event event = Webhook.constructEvent(
                    jsonPayload, sigHeader, endpointSecret
            );
            log.info("stripe signature varified: => Id: {} | Type: {}", event.getId(), event.getType());
            return event;
        } catch (Exception e) {
            // Invalid payload
            throw new Error_Response(ErrorCode.INVALID_STRIPE_SIGNATURE.getErrorCode(), ErrorCode.INVALID_STRIPE_SIGNATURE.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
