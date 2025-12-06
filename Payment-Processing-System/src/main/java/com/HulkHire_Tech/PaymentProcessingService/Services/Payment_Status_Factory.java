package com.HulkHire_Tech.PaymentProcessingService.Services;

import com.HulkHire_Tech.PaymentProcessingService.Constant.TransactionStatusEnum;
import com.HulkHire_Tech.PaymentProcessingService.Transection.Processor.*;
import com.HulkHire_Tech.PaymentProcessingService.Transection.TrxStatusProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Payment_Status_Factory {

    private final ApplicationContext Context;
    public TrxStatusProcessor getStatusProcessers(TransactionStatusEnum TxnStatus) {
        switch (TxnStatus) {
            case CREATED:
                log.info("=> case 1");
                return Context.getBean(CreatedStatusProcessor.class);
            case INITIATED:
                log.info("=> case 2");
                return Context.getBean(InitiatedStatusProcessor.class);
            case PENDING:
                log.info("=> case 3");
                return Context.getBean(PendingStatusProcessor.class);
            case SUCCESS:
                log.info("=> case 4");
                return Context.getBean(SucessStatusProcessor.class);
            case FAILED:
                log.info("=> case 5");
                return Context.getBean((FailedStatusProcessor.class));
            default:
                return null; // or throw an exception if the status ID is invalid
        }
    }
}
