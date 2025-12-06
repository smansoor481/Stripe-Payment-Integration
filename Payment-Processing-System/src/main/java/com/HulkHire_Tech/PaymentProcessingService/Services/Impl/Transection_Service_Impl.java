package com.HulkHire_Tech.PaymentProcessingService.Services.Impl;

import com.HulkHire_Tech.PaymentProcessingService.Constant.TransactionStatusEnum;
import com.HulkHire_Tech.PaymentProcessingService.Http.HttpRequestData;
import com.HulkHire_Tech.PaymentProcessingService.Http.HttpServiceEngine;
import com.HulkHire_Tech.PaymentProcessingService.Responce_Pojo.CreataTexRequest;
import com.HulkHire_Tech.PaymentProcessingService.Responce_Pojo.FinalTrxResponce;
import com.HulkHire_Tech.PaymentProcessingService.Responce_Pojo.InitiateTexRequest;
import com.HulkHire_Tech.PaymentProcessingService.Services.Interface.Payment_Status_Service;
import com.HulkHire_Tech.PaymentProcessingService.Services.Interface.Transection_Service;
import com.HulkHire_Tech.PaymentProcessingService.Stripe_Provider.StripeFinalResponce;
import com.HulkHire_Tech.PaymentProcessingService.Transection.DTO.TransectionDTO;
import com.HulkHire_Tech.PaymentProcessingService.Transection.TransectionDAO_Repository.TransectionDAO_Repository;
import com.HulkHire_Tech.PaymentProcessingService.Transection.Transection_Entity;
import com.HulkHire_Tech.PaymentProcessingService.Utils.Helpers.StripePaymentHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class Transection_Service_Impl implements Transection_Service {
    private final Payment_Status_Service paymentStatusService;
    private final ModelMapper modelMapper;
    private final TransectionDAO_Repository transectionDAORepository;
    private final HttpServiceEngine httpServiceEngine;
    private final StripePaymentHelper stripePaymentHelper;


    @Override
    public FinalTrxResponce creataTrx(CreataTexRequest creataTexRequest) {
        //LOGIC
        TransectionDTO Trxdto = modelMapper.map(creataTexRequest, TransectionDTO.class);
        String TrxStatus = TransactionStatusEnum.CREATED.getName(); // Assuming 1 represents the "Created" status
        Trxdto.setTxnStatus(TrxStatus);
        // Generate a unique transaction reference (you can use UUID or any other method)
        String TrxRefrence = UUID.randomUUID().toString();
        Trxdto.setTxnReference(TrxRefrence);
        log.info("=> TransectionDTO: {} ", Trxdto);

        //save txn record in DB. as CREATED
        //int TrnStatusId = 1; // CREATED
        TransectionDTO Responce =  paymentStatusService.processStatus(Trxdto);
        log.info("=> TransectionDTO: {} ", Responce);

        FinalTrxResponce finalResponce = FinalTrxResponce.builder()
                .txnReference(Responce.getTxnReference())
                .txnStatus(Responce.getTxnStatus())// No redirect URL at this stage
                .build();

        log.info("=> Final Responce: {} ", finalResponce);
        return finalResponce;
    }
    //---------------------------------------------------------------------------------------------------
    @Override
    public FinalTrxResponce initiateTrx(String txnReference, InitiateTexRequest initiateTexRequest) {
        //LOGIC
        log.info("=> Transaction Initiated form Service");
        //LOGIC
        Transection_Entity TrxEntity = transectionDAORepository
                .getTransectionByTransectionReference(txnReference);

        TransectionDTO Trxdto = modelMapper.map(TrxEntity, TransectionDTO.class);

        String TrxStatus = TransactionStatusEnum.INITIATED.getName(); // Assuming 2 represents the "Initiated" status
        Trxdto.setTxnStatus(TrxStatus);
        log.info("=> case: {} ", Trxdto);
        //---------------------------------------------------------------------------------------------------
        //Making a http call for the Stripe API for payment processing

        HttpRequestData PreparedHttpRequest = stripePaymentHelper
                .PrapereHttpRequest(initiateTexRequest);
        log.info("=> PreparedHttpRequest: {} ", PreparedHttpRequest);

        ResponseEntity<String> paymentResponce = httpServiceEngine
                .MakingHttpCall(PreparedHttpRequest);

        StripeFinalResponce stripeResponce = stripePaymentHelper
                .processResponce(paymentResponce);
        //---------------------------------------------------------------------------------------------------
        // Assuming 3 represents the "Pending" status
        Trxdto.setTxnStatus(TransactionStatusEnum.PENDING.getName());
        Trxdto.setProviderReference(stripeResponce.getId());
        log.info("=> case: {} ", Trxdto);
        paymentStatusService.processStatus(Trxdto);

        FinalTrxResponce finalResponce = FinalTrxResponce.builder()
                .txnReference(Trxdto.getTxnReference())
                .txnStatus(Trxdto.getTxnStatus())
                .redirectUrl(stripeResponce.getUrl())
                .build();
        log.info("=> Final Responce: {} ", finalResponce);

        return finalResponce;
    }

    @Override
    public String successTrx(String txnReference) {
        log.info("=> Transaction Success form Service");
        return "";
    }
}

