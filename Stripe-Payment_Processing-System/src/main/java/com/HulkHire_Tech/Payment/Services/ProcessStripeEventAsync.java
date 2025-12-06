package com.HulkHire_Tech.Payment.Services;

import com.HulkHire_Tech.Payment.Helpers.Stripe_Webhook_Helper;
import com.HulkHire_Tech.Payment.Http.HttpRequestData;
import com.HulkHire_Tech.Payment.Http.HttpServiceEngine;
import com.HulkHire_Tech.Payment.Responce_Pojo.Stripe_Response;
import com.HulkHire_Tech.Payment.Util.JSON_Util;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProcessStripeEventAsync {

    private final Stripe_Webhook_Helper stripeWebhookHelper;
    private final HttpServiceEngine httpServiceEngine;
    private final JSON_Util jsonUtil;

     private List<String> SuccessEvent = Arrays.asList(
            "checkout.session.completed",
            "checkout.session.async_payment_succeeded"
    );
    private List<String> FailedEvent = Arrays.asList(
            "checkout.session.async_payment_failed"
    );

    @Async
    public void ProcessingResponceEvent(Event event) {
        //Read Incoming data
        //.object
        //Understand Witch Event

        if (!SuccessEvent.contains(event.getType()) && !FailedEvent.contains(event.getType())) {
            log.info("=> Event type {} is not in the allowed list. Skipping processing.", event.getType());
            return;
        }

        log.info("=> Processing incoming event:");

        // Get the deserialized object from the event
        String EventAsJSON = event.getDataObjectDeserializer().getRawJson();

        //Convert JSON to Java Object
        Stripe_Response StripeRespoceObject = jsonUtil.ConvertJsonToJavaObject(
                EventAsJSON, Stripe_Response.class);
        log.info("StripeRespoceObject In ProcessingResponceEvent: {}", StripeRespoceObject);

        if(SuccessEvent.contains(event.getType())) {
            if(StripeRespoceObject.getPayment_status().equals("paid")) {
                //Call the Payment Processing Service to Update Transaction Status to Success
                //* Make API Call to Success Case

                trigerSuccessNotification(StripeRespoceObject);

            } else {
                log.warn("=> Payment status is not 'paid' for event type {}. Current status: {}", event.getType(), StripeRespoceObject.getPayment_status());
            }
            return;
        }

        if (FailedEvent.contains(event.getType())) {
            //Call the Payment Processing Service to Update Transaction Status to Failed
            //* Make API Call to Failure Case

            trigerFailureNotification(StripeRespoceObject);

            log.error("=> Payment failed for event type {}.", event.getType());
        }
    }

    private void trigerSuccessNotification(Stripe_Response stripeRespoceObject) {
        // Prepare Http Request for Success Notification Case using Helper Class
        log.info("-------> Stripe Respoce Object in Success Notification: {}", stripeRespoceObject);
        HttpRequestData PraperedHttpRequest = stripeWebhookHelper.PrepareHttpRequestForSuccessNotification(stripeRespoceObject);

        //Making API Call to Payment Processing Service
        ResponseEntity<String> NotificationCall = httpServiceEngine.MakingHttpCall(PraperedHttpRequest);
            log.info("=> Success Notification Call for Payment Processing Services : {}", NotificationCall);

        if(NotificationCall.getStatusCode().is2xxSuccessful()){
            log.info("=> Success Notification sent successfully.");
        } else {
            log.error("=> Failed to send Success Notification. Status Code: {}", NotificationCall.getStatusCode());
        }
    }

    private void trigerFailureNotification(Stripe_Response stripeRespoceObject) {
        // Prepare Http Request for Failed Notification Case using Helper Class
        log.info("-------> Stripe Respoce Object in failed Notification: {}", stripeRespoceObject);
        HttpRequestData PraperedHttpRequest = stripeWebhookHelper.PrepareHttpRequestForFailedNotification(stripeRespoceObject);

        //Making API Call to Payment Processing Service
        ResponseEntity<String> NotificationCall = httpServiceEngine.MakingHttpCall(PraperedHttpRequest);
            log.info("=> Failure Notification Call for Payment Processing Services : {}", NotificationCall);

        if(NotificationCall.getStatusCode().is2xxSuccessful()){
            log.info("=> Failure Notification sent successfully.");
        } else {
            log.error("=> Failed to send Failure Notification. Status Code: {}", NotificationCall.getStatusCode());
        }
    }
}