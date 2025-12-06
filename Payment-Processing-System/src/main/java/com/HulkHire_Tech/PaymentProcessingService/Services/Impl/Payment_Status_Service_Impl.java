package com.HulkHire_Tech.PaymentProcessingService.Services.Impl;

import com.HulkHire_Tech.PaymentProcessingService.Constant.TransactionStatusEnum;
import com.HulkHire_Tech.PaymentProcessingService.Services.Interface.Payment_Status_Service;
import com.HulkHire_Tech.PaymentProcessingService.Services.Payment_Status_Factory;
import com.HulkHire_Tech.PaymentProcessingService.Transection.DTO.TransectionDTO;
import com.HulkHire_Tech.PaymentProcessingService.Transection.TrxStatusProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Payment_Status_Service_Impl implements Payment_Status_Service {

    private final Payment_Status_Factory paymentStatusFactory;


    @Override
    public TransectionDTO processStatus(TransectionDTO TrxDto) {
        log.info("=> Payment_Status_Service_Impl");

        TransactionStatusEnum status = TransactionStatusEnum.fromName(TrxDto.getTxnStatus());

        TrxStatusProcessor StatusResponce = paymentStatusFactory.getStatusProcessers(status);

        if(paymentStatusFactory == null){
            throw new RuntimeException("Invalid Status ID");
        }

        TransectionDTO responce = StatusResponce.processStatus(TrxDto);
        log.info("=> PaymentStatusResponce: {}", responce);

        return responce;
    }
}
