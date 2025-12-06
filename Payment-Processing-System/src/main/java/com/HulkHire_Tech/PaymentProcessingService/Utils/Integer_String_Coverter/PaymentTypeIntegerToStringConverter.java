package com.HulkHire_Tech.PaymentProcessingService.Utils.Integer_String_Coverter;

import com.HulkHire_Tech.PaymentProcessingService.Constant.PaymentTypeEnum;
import org.modelmapper.AbstractConverter;

public class PaymentTypeIntegerToStringConverter extends AbstractConverter<Integer, String> {
    @Override
    protected String convert(Integer source) {
        return PaymentTypeEnum.fromId(source).getName();
    }
}