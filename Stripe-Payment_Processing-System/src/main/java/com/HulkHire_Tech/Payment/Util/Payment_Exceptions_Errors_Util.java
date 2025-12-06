package com.HulkHire_Tech.Payment.Util;

import com.HulkHire_Tech.Payment.Constants.ErrorCode;
import com.HulkHire_Tech.Payment.Pojo.Create_Payment_Request;
import com.HulkHire_Tech.Payment.Pojo.Error_Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class Payment_Exceptions_Errors_Util {
    public String validatePaymentRequest(Create_Payment_Request CreatePaymentRequest) {
        // if CreatePaymentRequest Second line_item Quantity < 0 or Quantity == 0 then throw exception
        if (CreatePaymentRequest.getLineItems().isEmpty() ||
                CreatePaymentRequest.getLineItems().stream().anyMatch(item -> item.getQuantity() <= 0)) {
            throw new Error_Response(ErrorCode.INVALID_QUANTITY.getErrorCode(), ErrorCode.INVALID_QUANTITY.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        //if CeratePaymentRequest first line_item currency is != "USD" then throw exception
        if (!CreatePaymentRequest.getLineItems().get(0).getCurrency().equals("USD")) {
            throw new Error_Response(ErrorCode.INVALID_CURRENCY.getErrorCode(), ErrorCode.INVALID_CURRENCY.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        return "Valid Request";
    }
}
