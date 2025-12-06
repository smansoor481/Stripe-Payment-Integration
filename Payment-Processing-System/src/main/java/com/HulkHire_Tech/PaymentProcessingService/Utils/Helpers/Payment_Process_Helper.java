package com.HulkHire_Tech.PaymentProcessingService.Utils.Helpers;

import com.HulkHire_Tech.PaymentProcessingService.Constant.TransactionStatusEnum;
import com.HulkHire_Tech.PaymentProcessingService.Transection.DTO.TransectionDTO;
import com.HulkHire_Tech.PaymentProcessingService.Transection.TransectionDAO_Repository.TransectionDAO_Repository;
import com.HulkHire_Tech.PaymentProcessingService.Transection.Transection_Entity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class Payment_Process_Helper {

    private final TransectionDAO_Repository transectionDAORepository;
    private final ModelMapper modelMapper;

    private final List<TransactionStatusEnum> LIST_OF_FINAL_STATUS = List.of(
            TransactionStatusEnum.SUCCESS,
            TransactionStatusEnum.FAILED
    );

    public boolean isTransactionInFinalState(TransectionDTO txnDto) {
        Transection_Entity existingTxnObj = transectionDAORepository.getTransectionByTransectionReference(txnDto.getTxnReference());
        TransectionDTO existingTxnDto = modelMapper.map(existingTxnObj, TransectionDTO.class);

        if(LIST_OF_FINAL_STATUS.contains(TransactionStatusEnum.fromName(
                existingTxnDto.getTxnStatus()))){
            log.info("Transaction already in final status: {}. No update performed.", existingTxnDto.getTxnStatus());
            return true;
        }
        log.info("Transaction not in final status: {}. Proceeding with update.", existingTxnDto.getTxnStatus());
        return false;
    }
}
