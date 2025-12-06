package com.HulkHire_Tech.PaymentProcessingService.Services.Interface;

import com.HulkHire_Tech.PaymentProcessingService.Transection.DTO.TransectionDTO;
import org.springframework.stereotype.Service;

@Service
public interface Payment_Status_Service {
    public TransectionDTO processStatus(TransectionDTO txnStatusId);
}
