package com.HulkHire_Tech.PaymentProcessingService.Config;

import com.HulkHire_Tech.PaymentProcessingService.Transection.DTO.TransectionDTO;
import com.HulkHire_Tech.PaymentProcessingService.Transection.Transection_Entity;
import com.HulkHire_Tech.PaymentProcessingService.Utils.Integer_String_Coverter.PaymentMethodIntegerToStringConverter;
import com.HulkHire_Tech.PaymentProcessingService.Utils.Integer_String_Coverter.PaymentTypeIntegerToStringConverter;
import com.HulkHire_Tech.PaymentProcessingService.Utils.Integer_String_Coverter.ProviderIntegerToStringConverter;
import com.HulkHire_Tech.PaymentProcessingService.Utils.Integer_String_Coverter.TransactionStatusIntegerToStringConverter;
import com.HulkHire_Tech.PaymentProcessingService.Utils.String_Integer_Converter.PaymentMethodEnumConverter;
import com.HulkHire_Tech.PaymentProcessingService.Utils.String_Integer_Converter.PaymentTypeConverter;
import com.HulkHire_Tech.PaymentProcessingService.Utils.String_Integer_Converter.ProviderConverter;
import com.HulkHire_Tech.PaymentProcessingService.Utils.String_Integer_Converter.TransactionStatusConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AppConfig {
    @Bean
    ModelMapper modelMapper(){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);
        // For String to Integer Enum conversion
        Converter<String, Integer>paymentMethodEnumConverter = new PaymentMethodEnumConverter();
        Converter<String, Integer>paymentTypeConverter = new PaymentTypeConverter();
        Converter<String, Integer>providerConverter = new ProviderConverter();
        Converter<String, Integer>transactionStatusConverter = new TransactionStatusConverter();

        // For Integer to String Enum conversion
        Converter<Integer, String>paymentMethodIntegerToStringConverter = new PaymentMethodIntegerToStringConverter();
        Converter<Integer, String>paymentTypeIntegerToStringConverter = new PaymentTypeIntegerToStringConverter();
        Converter<Integer, String>providerIntegerToStringConverter = new ProviderIntegerToStringConverter();
        Converter<Integer, String>transactionStatusIntegerToStringConverter = new TransactionStatusIntegerToStringConverter();

        mapper.addMappings(new PropertyMap<TransectionDTO, Transection_Entity>() {
            @Override
            protected void configure() {
                // For String to Integer Enum conversion
                using(paymentMethodEnumConverter).map(source.getPaymentMethod(), destination.getPaymentMethodId());
                using(paymentTypeConverter).map(source.getPaymentType(), destination.getPaymentTypeId());
                using(providerConverter).map(source.getProvider(), destination.getProviderId());
                using(transactionStatusConverter).map(source.getTxnStatus(), destination.getTxnStatusId());
            }
        });

        mapper.addMappings(new PropertyMap<Transection_Entity, TransectionDTO>() {
            @Override
            protected void configure() {
                //For Integer to String Enum conversion
                using(paymentMethodIntegerToStringConverter).map(source.getPaymentMethodId(), destination.getPaymentMethod());
                using(paymentTypeIntegerToStringConverter).map(source.getPaymentTypeId(), destination.getPaymentType());
                using(providerIntegerToStringConverter).map(source.getProviderId(), destination.getProvider());
                using(transactionStatusIntegerToStringConverter).map(source.getTxnStatusId(), destination.getTxnStatus());
            }
        });
        return mapper;
    }
}
