package com.HulkHire_Tech.PaymentProcessingService.Utils.Integer_String_Coverter;

import com.HulkHire_Tech.PaymentProcessingService.Constant.ProviderEnum;
import org.modelmapper.AbstractConverter;

public class ProviderIntegerToStringConverter extends AbstractConverter<Integer, String> {
    @Override
    protected String convert(Integer source) {
        return ProviderEnum.fromId(source).getName();
    }
}
