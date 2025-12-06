package com.HulkHire_Tech.PaymentProcessingService.Constant;

import lombok.Getter;

import java.sql.SQLException;

@Getter
public enum ErrorCode {
    GENERIC_ERROR("70001", "Unable to process the request at this moment try again later"),
    UNABLE_TO_CONNECT_PAYMENT_SERVICE("70002", "Unable to connect to http Paymetn service"),
    JSON_BODY_NULL("60001", "Json Request body is null"),
    STRIPE_RESPONSE_NULL("60007", "Stripe response is null"),
    INVALID_QUANTITY("60002", "Invalid quantity is provided"),
    INVALID_CURRENCY("60003", "Invalid currency is provided Currency = USD"),
    STRIPE_ERROR("60004", "<Dynamically Prepared from Stripe Response>"),
    EXPIRE_PAYEMTN_FAILED("60005", "Expire payment failed"),
    PAYMENT_PROCESSING_FAILED("60006", "Payment processing failed");

    private final String errorCode;
    private final String errorMessage;

    ErrorCode(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
