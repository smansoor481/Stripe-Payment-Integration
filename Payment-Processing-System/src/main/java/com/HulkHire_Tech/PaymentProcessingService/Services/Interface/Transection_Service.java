package com.HulkHire_Tech.PaymentProcessingService.Services.Interface;

import com.HulkHire_Tech.PaymentProcessingService.Responce_Pojo.CreataTexRequest;
import com.HulkHire_Tech.PaymentProcessingService.Responce_Pojo.FinalTrxResponce;
import com.HulkHire_Tech.PaymentProcessingService.Responce_Pojo.InitiateTexRequest;
import com.HulkHire_Tech.PaymentProcessingService.Stripe_Provider.StripeFinalResponce;
import org.springframework.stereotype.Service;

@Service
public interface Transection_Service {
    FinalTrxResponce creataTrx(CreataTexRequest creataTexRequest);

    FinalTrxResponce initiateTrx(String txnReference, InitiateTexRequest initiateTexRequest);

    String successTrx (String txnReference);
}
