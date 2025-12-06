package com.HulkHire_Tech.Payment.Constants;

import lombok.Getter;

@Getter
public enum ErrorCode {INVALID_QUANTITY("40001", "Invalid quantity is provided"),
    INVALID_CURRENCY("40002", "Invalid currency is provided Currency = USD"),
    GENERIC_ERROR("50001", "Unable to process the request at this moment try again later"),
    UNABLE_TO_CONNECT_STRIPE_SERVICE("40003", "Unable to connect to Stripe service"),
    STRIPE_ERROR("40004", "<Dynamically Prepared from Stripe Response>"),
    PAYMENT_CREATION_FAILED("40005", "Payment creation failed"),
    RETRIVE_PAYMENT_FAILED("40006", "Retrieve payment failed"),
    EXPIRE_PAYEMTNT_FAILED("40007", "Expire payment failed"),
    INVALID_STRIPE_SIGNATURE("40008", "Invalid stripe signature"),
    PAYMENT_FAILED_TO_PROCESSED("40009", "Failed to send notification");

    private final String errorCode;
    private final String errorMessage;

    ErrorCode(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
