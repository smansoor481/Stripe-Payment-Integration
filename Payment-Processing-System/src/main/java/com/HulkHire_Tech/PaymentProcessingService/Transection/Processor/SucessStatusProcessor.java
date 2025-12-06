package com.HulkHire_Tech.PaymentProcessingService.Transection.Processor;

import com.HulkHire_Tech.PaymentProcessingService.Transection.DTO.TransectionDTO;
import com.HulkHire_Tech.PaymentProcessingService.Transection.TransectionDAO_Repository.TransectionDAO_Repository;
import com.HulkHire_Tech.PaymentProcessingService.Transection.Transection_Entity;
import com.HulkHire_Tech.PaymentProcessingService.Transection.TrxStatusProcessor;
import com.HulkHire_Tech.PaymentProcessingService.Utils.Helpers.Payment_Process_Helper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SucessStatusProcessor implements TrxStatusProcessor {
    private final TransectionDAO_Repository transectionDAORepository;
    private final Payment_Process_Helper paymentProcessHelper;
    private final ModelMapper modelMapper;
    @Override
    public TransectionDTO processStatus(TransectionDTO trxDto) {
        log.info("=> SucessStatusProcessor");

        if(paymentProcessHelper.isTransactionInFinalState(trxDto)){
            log.warn("=> Transaction is already in a final state. No update performed for txnReference: {}",
                    trxDto.getTxnReference());
            return trxDto;
        }

        Transection_Entity TrxEntity = modelMapper.map(trxDto, Transection_Entity.class);

        transectionDAORepository.updateTransectionByTransectionReference(TrxEntity);

        log.info("=> Updated transaction status successfully for txnReference: {}",
                trxDto.getTxnReference());

        return trxDto;
    }
}
