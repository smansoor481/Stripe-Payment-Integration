package com.HulkHire_Tech.PaymentProcessingService.Utils.Integer_String_Coverter;

import com.HulkHire_Tech.PaymentProcessingService.Constant.PaymentMethodEnums;
import org.modelmapper.AbstractConverter;

public class PaymentMethodIntegerToStringConverter extends AbstractConverter<Integer, String> {
    @Override
    protected String convert(Integer source) {
        return PaymentMethodEnums.fromId(source).getName();
    }
}
