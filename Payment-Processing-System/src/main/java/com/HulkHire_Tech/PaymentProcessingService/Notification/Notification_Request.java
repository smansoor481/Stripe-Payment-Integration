package com.HulkHire_Tech.PaymentProcessingService.Notification;

import lombok.Data;

import java.util.Map;

@Data
public class Notification_Request {
    private String providerReference; // Incoming Request ID

    private String trxReference;  // Incoming Request Transaction ID

    private String provider; // Stripe, PayPal, Razorpay etc. | Provider Name

    private String notificationType; // PAYMENT_SUCCESS, PAYMENT_FAILED

    private Map<String, String> payload;

}
