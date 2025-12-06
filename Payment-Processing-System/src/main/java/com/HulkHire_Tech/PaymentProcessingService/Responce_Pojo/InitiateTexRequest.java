package com.HulkHire_Tech.PaymentProcessingService.Responce_Pojo;

import lombok.Data;
import java.util.List;

@Data
public class InitiateTexRequest {
    private String successUrl;
    private String cancelUrl;

    private List<Line_Items> lineItems;

}
