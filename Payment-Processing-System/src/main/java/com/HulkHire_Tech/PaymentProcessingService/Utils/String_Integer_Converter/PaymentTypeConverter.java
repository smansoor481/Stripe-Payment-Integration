package com.HulkHire_Tech.PaymentProcessingService.Utils.String_Integer_Converter;

import com.HulkHire_Tech.PaymentProcessingService.Constant.PaymentTypeEnum;
import org.modelmapper.AbstractConverter;

public class PaymentTypeConverter extends AbstractConverter<String, Integer> {
    @Override
    protected Integer convert(String source) {
        return PaymentTypeEnum.fromName(source).getId();
    }
}