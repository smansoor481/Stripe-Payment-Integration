package com.HulkHire_Tech.PaymentProcessingService.Utils.Integer_String_Coverter;

import com.HulkHire_Tech.PaymentProcessingService.Constant.TransactionStatusEnum;
import org.modelmapper.AbstractConverter;

public class TransactionStatusIntegerToStringConverter extends AbstractConverter<Integer, String> {
    @Override
    protected String convert(Integer source) {
        return TransactionStatusEnum.fromId(source).getName();
    }
}