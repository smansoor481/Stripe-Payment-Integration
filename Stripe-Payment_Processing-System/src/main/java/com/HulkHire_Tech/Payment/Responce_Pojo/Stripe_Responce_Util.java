package com.HulkHire_Tech.Payment.Responce_Pojo;

import org.springframework.stereotype.Component;

@Component
public class Stripe_Responce_Util {
    public static Payment_Response ConvertToPaymentResponse(Stripe_Response stripeResponse) {
        Payment_Response paymentResponse = new Payment_Response();
        paymentResponse.setId(stripeResponse.getId());
        paymentResponse.setSessionStatus(stripeResponse.getStatus());
        paymentResponse.setPaymentStatus(stripeResponse.getPayment_status());
        paymentResponse.setUrl(stripeResponse.getUrl());
        return paymentResponse;
    }
}
