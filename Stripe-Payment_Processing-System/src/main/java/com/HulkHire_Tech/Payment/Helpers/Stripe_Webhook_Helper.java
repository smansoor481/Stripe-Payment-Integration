package com.HulkHire_Tech.Payment.Helpers;

import com.HulkHire_Tech.Payment.Constants.Constant;
import com.HulkHire_Tech.Payment.Constants.ErrorCode;
import com.HulkHire_Tech.Payment.Enums.NotificationType;
import com.HulkHire_Tech.Payment.Http.HttpRequestData;
import com.HulkHire_Tech.Payment.Pojo.Notification_Request;
import com.HulkHire_Tech.Payment.Responce_Pojo.Stripe_Response;
import com.HulkHire_Tech.Payment.Util.JSON_Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class Stripe_Webhook_Helper {


    @Value("${payment.processing.notification.url}")
    private String PaymentProcessingNotificationURL;

    private final JSON_Util jsonUtil;

    public HttpRequestData PrepareHttpRequestForSuccessNotification(Stripe_Response stripeResponseObject) {
        log.info("Preparing HttpRequest for Stripe API call");
        HttpHeaders headers = new HttpHeaders();

        // Set content type to JSON as required by Payment Processing Notification Service
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Prepare formdata
        Notification_Request formData = new Notification_Request();
        formData.setNotificationType(NotificationType.PAYMENT_SUCCESS.getName());
        formData.setProvider(Constant.STRIPE);
        formData.setProviderReference(stripeResponseObject.getId());

        String formDataAsJson =  jsonUtil.ConvertJavaObjectToJson(formData);

        HttpRequestData requestData = new HttpRequestData();
        requestData.setHttpMethod(HttpMethod.POST);
        requestData.setUrl(PaymentProcessingNotificationURL);
        requestData.setHttpHeaders(headers);
        requestData.setRequestBody(formDataAsJson);

        log.info("=> Prepared HttpRequest for Success Notification: {}", requestData);
        return requestData;
    }
    public HttpRequestData PrepareHttpRequestForFailedNotification(Stripe_Response stripeResponseObject) {
        log.info("Preparing HttpRequest for Stripe API call");
        HttpHeaders headers = new HttpHeaders();

        // Set content type to JSON as required by Payment Processing Notification Service
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Prepare formdata
        Notification_Request formData = new Notification_Request();
        formData.setNotificationType(NotificationType.PAYMENT_FAILED.getName());
        formData.setProvider(Constant.STRIPE);
        formData.setProviderReference(stripeResponseObject.getId());

        Map<String, String> payload = new HashMap<>();
        payload.put(Constant.ERROR_CODE, ErrorCode.PAYMENT_FAILED_TO_PROCESSED.getErrorCode());
        payload.put(Constant.ERROR_MESSAGE, ErrorCode.PAYMENT_FAILED_TO_PROCESSED.getErrorMessage());
        formData.setPayload(payload);

        String formDataAsJson =  jsonUtil.ConvertJavaObjectToJson(formData);

        HttpRequestData requestData = new HttpRequestData();
        requestData.setHttpMethod(HttpMethod.POST);
        requestData.setUrl(PaymentProcessingNotificationURL);
        requestData.setHttpHeaders(headers);
        requestData.setRequestBody(formDataAsJson);

        log.info("=> Prepared HttpRequest for failed Notification: {}", requestData);
        return requestData;
    }
}
