package com.HulkHire_Tech.PaymentProcessingService.Responce_Pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FinalTrxResponce {
    private String txnStatus;
    private String txnReference;
    private String redirectUrl;
}
