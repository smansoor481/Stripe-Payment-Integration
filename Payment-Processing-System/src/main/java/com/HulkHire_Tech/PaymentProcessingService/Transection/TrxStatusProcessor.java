package com.HulkHire_Tech.PaymentProcessingService.Transection;

import com.HulkHire_Tech.PaymentProcessingService.Transection.DTO.TransectionDTO;
import org.springframework.stereotype.Service;

@Service
public interface TrxStatusProcessor {
    public TransectionDTO processStatus(TransectionDTO trxDto);
}