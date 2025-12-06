package com.HulkHire_Tech.PaymentProcessingService.Services.Impl;

import com.HulkHire_Tech.PaymentProcessingService.Constant.Constant;
import com.HulkHire_Tech.PaymentProcessingService.Constant.NotificationType;
import com.HulkHire_Tech.PaymentProcessingService.Constant.ProviderEnum;
import com.HulkHire_Tech.PaymentProcessingService.Constant.TransactionStatusEnum;
import com.HulkHire_Tech.PaymentProcessingService.Notification.Notification_Request;
import com.HulkHire_Tech.PaymentProcessingService.Services.Interface.Notification_Service;
import com.HulkHire_Tech.PaymentProcessingService.Services.Interface.Payment_Status_Service;
import com.HulkHire_Tech.PaymentProcessingService.Transection.DTO.TransectionDTO;
import com.HulkHire_Tech.PaymentProcessingService.Transection.TransectionDAO_Repository.TransectionDAO_Repository;
import com.HulkHire_Tech.PaymentProcessingService.Transection.Transection_Entity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class Notification_Service_Impl implements Notification_Service {
    private final TransectionDAO_Repository transectionDAO_repository;
    private final ModelMapper modelMapper;
    private final Payment_Status_Service paymentStatusService;


    @Override
    public void ProcessNotification(Notification_Request notificationRequest) {
        log.info("=> Processing Notification Request: {}", notificationRequest);

        //Gating Provider Enum - Stripe or PayPal
        ProviderEnum provider = ProviderEnum.fromName(notificationRequest.getProvider());

        // Transaction Entity from DB Contains the Integer data.
        Transection_Entity TrxEntity = transectionDAO_repository.getTransectionByProviderReference(
                notificationRequest.getProviderReference(), provider.getId());

        //Convert That Integer Data to String Data In DTO
        TransectionDTO TrxDTO = modelMapper.map(TrxEntity, TransectionDTO.class);

        //Gating Notification Type - Payment Success or Payment Failure
        NotificationType notificationType = NotificationType.fromName(notificationRequest.getNotificationType());

        switch (notificationType){
            case PAYMENT_SUCCESS:
                log.info("=> Handled PAYMENT_SUCCESS Notification");
                //* Process Payment Success
                ProcessPaymentSuccess(TrxDTO);
                break;
            case PAYMENT_FAILED:
                log.info("=> Handling PAYMENT_FAILED Notification");
                //* Process Payment Failure
                ProcessPaymentFailed(notificationRequest, TrxDTO);
                break;
            default:
                log.warn("=> Unhandled Notification Type: {}", notificationType);
                break;
        }
        return;
    }

    private void ProcessPaymentSuccess(TransectionDTO TrxDTO) {
        TrxDTO.setTxnStatus(TransactionStatusEnum.SUCCESS.name());
        paymentStatusService.processStatus(TrxDTO);
        log.info("=> Transaction updated to SUCCESS: {}", TrxDTO);
    }

    private void ProcessPaymentFailed(Notification_Request notificationRequest, TransectionDTO TrxDTO) {
        TrxDTO.setTxnStatus(TransactionStatusEnum.FAILED.name());
        TrxDTO.setErrorCode(notificationRequest.getPayload().get(Constant.ERROR_CODE));
        TrxDTO.setErrorMessage(notificationRequest.getPayload().get(Constant.ERROR_MESSAGE));
        paymentStatusService.processStatus(TrxDTO);
        log.info("=> Handled PAYMENT_FAILED Notification");
    }
}
