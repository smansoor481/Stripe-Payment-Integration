package com.HulkHire_Tech.PaymentProcessingService.Transection.Processor;

import com.HulkHire_Tech.PaymentProcessingService.Transection.DTO.TransectionDTO;
import com.HulkHire_Tech.PaymentProcessingService.Transection.TransectionDAO_Repository.TransectionDAO_Repository;
import com.HulkHire_Tech.PaymentProcessingService.Transection.Transection_Entity;
import com.HulkHire_Tech.PaymentProcessingService.Transection.TrxStatusProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InitiatedStatusProcessor implements TrxStatusProcessor {
    private final TransectionDAO_Repository transectionDAORepository;
    private final ModelMapper modelMapper;

    @Override
    public TransectionDTO processStatus(TransectionDTO trxDto) {
        log.info("=> InitiatedStatusProcessor {} ", trxDto);

        Transection_Entity Trxdto = modelMapper.map(trxDto, Transection_Entity.class);

        transectionDAORepository.updateTransectionByTransectionReference(Trxdto);

        return trxDto;
    }
}
