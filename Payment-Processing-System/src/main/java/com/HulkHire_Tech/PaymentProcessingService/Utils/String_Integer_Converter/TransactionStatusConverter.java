package com.HulkHire_Tech.PaymentProcessingService.Utils.String_Integer_Converter;

import com.HulkHire_Tech.PaymentProcessingService.Constant.TransactionStatusEnum;
import org.modelmapper.AbstractConverter;
public class TransactionStatusConverter extends AbstractConverter<String, Integer> {
    @Override
    protected Integer convert(String source) {
        return TransactionStatusEnum.fromName(source).getId();
    }
}