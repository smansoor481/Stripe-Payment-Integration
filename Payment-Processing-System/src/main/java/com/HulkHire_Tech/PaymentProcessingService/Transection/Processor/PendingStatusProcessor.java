package com.HulkHire_Tech.PaymentProcessingService.Transection.Processor;

import com.HulkHire_Tech.PaymentProcessingService.Transection.DTO.TransectionDTO;
import com.HulkHire_Tech.PaymentProcessingService.Transection.TransectionDAO_Repository.TransectionDAO_Repository;
import com.HulkHire_Tech.PaymentProcessingService.Transection.Transection_Entity;
import com.HulkHire_Tech.PaymentProcessingService.Transection.TrxStatusProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PendingStatusProcessor implements TrxStatusProcessor {
    private final ModelMapper modelMapper;
    private final TransectionDAO_Repository transectionDAORepository;

    @Override
    public TransectionDTO processStatus(TransectionDTO trxDto) {
        log.info("=> PendingStatusProcessor");

        Transection_Entity Trxdto = modelMapper.map(trxDto, Transection_Entity.class);

        transectionDAORepository.updateTransectionByTransectionReference(Trxdto);

        return trxDto;
    }
}
