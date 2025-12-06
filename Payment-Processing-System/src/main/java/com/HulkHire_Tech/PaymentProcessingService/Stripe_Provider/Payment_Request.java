package com.HulkHire_Tech.PaymentProcessingService.Stripe_Provider;

import com.HulkHire_Tech.PaymentProcessingService.Responce_Pojo.Line_Items;
import lombok.Data;
import java.util.List;

@Data
public class Payment_Request {
    private String successUrl;
    private String cancelUrl;

    private List<Line_Items> lineItems;
}
