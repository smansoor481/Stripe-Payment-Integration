package com.HulkHire_Tech.PaymentProcessingService.Service;

import com.HulkHire_Tech.PaymentProcessingService.Constant.TransactionStatusEnum;
import com.HulkHire_Tech.PaymentProcessingService.Http.HttpServiceEngine;
import com.HulkHire_Tech.PaymentProcessingService.Responce_Pojo.CreataTexRequest;
import com.HulkHire_Tech.PaymentProcessingService.Responce_Pojo.FinalTrxResponce;
import com.HulkHire_Tech.PaymentProcessingService.Services.Impl.Transection_Service_Impl;
import com.HulkHire_Tech.PaymentProcessingService.Services.Interface.Payment_Status_Service;
import com.HulkHire_Tech.PaymentProcessingService.Transection.DTO.TransectionDTO;
import com.HulkHire_Tech.PaymentProcessingService.Transection.TransectionDAO_Repository.TransectionDAO_Repository;
import com.HulkHire_Tech.PaymentProcessingService.Utils.Helpers.StripePaymentHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class Transection_Service_ImplTest {

    @InjectMocks
    private Transection_Service_Impl transectionService;

    @Mock
    private Payment_Status_Service paymentStatusService;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private TransectionDAO_Repository transectionDAORepository;
    @Mock
    private HttpServiceEngine httpServiceEngine;
    @Mock
    private StripePaymentHelper stripePaymentHelper;

    @Test
    public void testcreataTrx() {
        //Arrange data for calling functional method
        CreataTexRequest creataTexRequest = new CreataTexRequest();

        TransectionDTO trxDTO = new TransectionDTO();
        modelMapper.map(creataTexRequest, TransectionDTO.class);
        when(modelMapper.map(creataTexRequest, TransectionDTO.class)).thenReturn(trxDTO);

        when(paymentStatusService.processStatus(trxDTO)).thenReturn(trxDTO);

        //ACT Calling functional method for testing
        FinalTrxResponce trxResponce = transectionService.creataTrx(creataTexRequest);

        //Assert Verification of result
        assertNotNull(trxResponce);
        assertNotNull(trxResponce.getTxnReference());
        assertNotNull(trxResponce.getTxnStatus());
        assertNull(trxResponce.getRedirectUrl());

        assertEquals("CREATED", trxResponce.getTxnStatus());
        assertTrue(Arrays.stream(TransactionStatusEnum.values())
                .anyMatch(status -> status.getName().equals(trxResponce.getTxnStatus())));
        assertEquals(36, trxResponce.getTxnReference().length());
        assertDoesNotThrow(() -> UUID.fromString(trxResponce.getTxnReference()));

        assertNotNull(trxDTO.getTxnReference());
        assertNotNull(trxDTO.getTxnStatus());

        verify(modelMapper, times(2)).map(creataTexRequest, TransectionDTO.class);

        verify(paymentStatusService, times(1)).processStatus(trxDTO);

        verifyNoMoreInteractions(modelMapper, paymentStatusService);

        log.info("\n Test method executed successfully.");
    }
}
