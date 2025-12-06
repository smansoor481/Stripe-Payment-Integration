package com.HulkHire_Tech.PaymentProcessingService.Stripe_Provider;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StripeFinalResponce {
    private String id;
//    private String txnStatus;
//    private String txnReference;
    private String sessionStatus;
    private String paymentStatus;
    private String url;
}
