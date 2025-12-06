package com.HulkHire_Tech.Payment.Services;

import com.HulkHire_Tech.Payment.Http.HttpRequestData;
import com.HulkHire_Tech.Payment.Http.HttpServiceEngine;
import com.HulkHire_Tech.Payment.Helpers.Create_Payment_Helper;
import com.HulkHire_Tech.Payment.Helpers.Expire_Payment_Helper;
import com.HulkHire_Tech.Payment.Helpers.Retrieve_Payment_Helper;
import com.HulkHire_Tech.Payment.Pojo.Create_Payment_Request;
import com.HulkHire_Tech.Payment.Responce_Pojo.Payment_Response;
import com.HulkHire_Tech.Payment.Responce_Pojo.Stripe_Responce_Util;
import com.HulkHire_Tech.Payment.Responce_Pojo.Stripe_Response;
import com.HulkHire_Tech.Payment.Util.JSON_Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Payment_Services_IMPL implements Payment_Services {

    private final HttpServiceEngine httpServiceEngine;
    private final JSON_Util jsonUtil;
    private final Create_Payment_Helper createPaymentHelper;
    private final Retrieve_Payment_Helper retrievePaymentHelper;
    private final Expire_Payment_Helper expirePaymentHelper;
    private final Stripe_Responce_Util stripeResponceUtil;


    @Override
    public Payment_Response CreatePayment(Create_Payment_Request CreatePaymentRequest) {
        log.info("=> Process payment creation logic here");

//      paymentExceptions.validatePaymentRequest(CreatePaymentRequest);

        HttpRequestData requestData = createPaymentHelper.PrepareHttpRequest(CreatePaymentRequest);

        ResponseEntity<String> httpResponce = httpServiceEngine.MakingHttpCall(requestData);
        log.info("=> Http Responce from external service: {}", httpResponce);

        Stripe_Response StripeResponse = createPaymentHelper.ProcessResponse(httpResponce);

        //Convert Stripe_Response to Payment_Response
        Payment_Response PaymentResponse = stripeResponceUtil.ConvertToPaymentResponse(StripeResponse);
        log.info("=> Converted to PaymentResponse: {}", PaymentResponse);

        return PaymentResponse;
    }

    @Override
    public Payment_Response RetrivePayment(String id) {
        log.info("=> Process payment Retrieve logic here");

        HttpRequestData requestData = retrievePaymentHelper.PrepareHttpRequest(id);

        ResponseEntity<String> httpResponce = httpServiceEngine.MakingHttpCall(requestData);
        log.info("=> Http Responce from external service: {}", httpResponce);

        Stripe_Response StripeResponse = retrievePaymentHelper.ProcessResponse(httpResponce);

        //Convert Stripe_Response to Payment_Response
        Payment_Response PaymentResponse = stripeResponceUtil.ConvertToPaymentResponse(StripeResponse);
        log.info("=> Converted to PaymentResponse: {}", PaymentResponse);

        return PaymentResponse;
    }

    @Override
    public Payment_Response ExpirePayment(String id) {
        log.info("=> Process payment Expire logic here");

        HttpRequestData requestData = expirePaymentHelper.PrepareHttpRequest(id);

        ResponseEntity<String> httpResponce = httpServiceEngine.MakingHttpCall(requestData);
        log.info("=> Http Responce from external service: {}", httpResponce);

        Stripe_Response StripeResponse = expirePaymentHelper.ProcessResponse(httpResponce);

        //Convert Stripe_Response to Payment_Response
        Payment_Response PaymentResponse = stripeResponceUtil.ConvertToPaymentResponse(StripeResponse);
        log.info("=> Converted to PaymentResponse: {}", PaymentResponse);

        return PaymentResponse;
    }
}
