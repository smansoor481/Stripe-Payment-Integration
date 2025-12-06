package com.HulkHire_Tech.PaymentProcessingService.Utils.Helpers;

import com.HulkHire_Tech.PaymentProcessingService.Constant.ErrorCode;
import com.HulkHire_Tech.PaymentProcessingService.Exception.Error_Response;
import com.HulkHire_Tech.PaymentProcessingService.Http.HttpRequestData;
import com.HulkHire_Tech.PaymentProcessingService.Responce_Pojo.InitiateTexRequest;
import com.HulkHire_Tech.PaymentProcessingService.Stripe_Provider.Payment_Request;
import com.HulkHire_Tech.PaymentProcessingService.Stripe_Provider.StripeFinalResponce;
import com.HulkHire_Tech.PaymentProcessingService.Transection.DTO.TransectionDTO;
import com.HulkHire_Tech.PaymentProcessingService.Utils.Json_Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StripePaymentHelper {
    private final ModelMapper modelMapper;
    private final Json_Util jsonUtil;


    @Value("${stripe.create.session.api.url}")
    private String StripeCreateSessionApiURL;

    public HttpRequestData PrapereHttpRequest(InitiateTexRequest initiateTexRequest) {
        //* This is the new Style for building the object by using a @Builder annotation in HttpRequestData class
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        Payment_Request paymentRequest = modelMapper.map(initiateTexRequest,Payment_Request.class);
        String JsonBody = jsonUtil.ConvertJavaObjectToJson(paymentRequest);
        log.info("=> JsonBody: {} ", JsonBody);

        //! Custom Exception handling for JsonBody is null
        if(JsonBody == null){
            log.error("=> JsonBody is null");
            throw new Error_Response(ErrorCode.JSON_BODY_NULL.getErrorCode(), ErrorCode.JSON_BODY_NULL.getErrorMessage(), HttpStatus.NO_CONTENT);
        }

        //Using Builder pattern to create HttpRequestData object
        HttpRequestData requestData = HttpRequestData.builder()
                .httpMethod(HttpMethod.POST)
                .url(StripeCreateSessionApiURL) // Example URL, replace with actual
                .httpHeaders(httpHeaders) // Set appropriate headers
                .requestBody(JsonBody) // Set appropriate body
                .build();

        return requestData;
    }

    public StripeFinalResponce processResponce(ResponseEntity<String> paymentResponse) {
        if (paymentResponse.getStatusCode() == HttpStatus.OK) {
            String StripeResponce = paymentResponse.getBody();

            StripeFinalResponce stripeFinalResponce = jsonUtil.ConvertJsonToJavaObject(StripeResponce,StripeFinalResponce.class);

            if(stripeFinalResponce != null && stripeFinalResponce.getId() != null && stripeFinalResponce.getUrl() != null && stripeFinalResponce.getSessionStatus() != null && stripeFinalResponce.getPaymentStatus() != null){
                log.info("*=> Payment processed successfully: {}", stripeFinalResponce);
                return stripeFinalResponce;
            }
            log.error("=> stripeFinalResponce is null");
            throw new Error_Response(ErrorCode.STRIPE_RESPONSE_NULL.getErrorCode(), ErrorCode.STRIPE_RESPONSE_NULL.getErrorMessage(), HttpStatus.NO_CONTENT);
            // Further processing of the response can be done here
        } else {
            log.error("Failed to process payment. Status Code: {}, Response Body: {}",
                    paymentResponse.getStatusCode(), paymentResponse.getBody());
            throw new Error_Response(ErrorCode.PAYMENT_PROCESSING_FAILED.getErrorCode(),
                    ErrorCode.PAYMENT_PROCESSING_FAILED.getErrorMessage(), HttpStatus.OK);
        }
    }
}
