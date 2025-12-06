package com.HulkHire_Tech.PaymentProcessingService.Utils.String_Integer_Converter;

import com.HulkHire_Tech.PaymentProcessingService.Constant.PaymentMethodEnums;
import org.modelmapper.AbstractConverter;

public class PaymentMethodEnumConverter extends AbstractConverter<String, Integer> {
    @Override
    protected Integer convert(String source) {
        return PaymentMethodEnums.fromName(source).getId();
    }
}
