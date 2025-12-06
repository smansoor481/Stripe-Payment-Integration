package com.HulkHire_Tech.PaymentProcessingService.Utils.String_Integer_Converter;

import com.HulkHire_Tech.PaymentProcessingService.Constant.ProviderEnum;
import org.modelmapper.AbstractConverter;

public class ProviderConverter extends AbstractConverter<String, Integer> {
    @Override
    protected Integer convert(String source) {
        return ProviderEnum.fromName(source).getId();
    }
}
