package com.HulkHire_Tech.Payment.Helpers;

import com.HulkHire_Tech.Payment.Constants.Constant;
import com.HulkHire_Tech.Payment.Constants.ErrorCode;
import com.HulkHire_Tech.Payment.Http.HttpRequestData;
import com.HulkHire_Tech.Payment.Pojo.Create_Payment_Request;
import com.HulkHire_Tech.Payment.Pojo.Error_Response;
import com.HulkHire_Tech.Payment.Responce_Pojo.Stripe_Response;
import com.HulkHire_Tech.Payment.Util.JSON_Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class Create_Payment_Helper {
    @Value("${stripe.secret.key}")
    private String StripeSecretKey;
    @Value("${stripe.create.session.url}")
    private String StripeCreateSessionURL;

    private final JSON_Util jsonUtil;

    //TODO We need to Give a valid Open AI API key in application.properties file to test this method
    private final ChatClient chatClient;

    public HttpRequestData PrepareHttpRequest(Create_Payment_Request CreatePaymentRequest) {
        log.info("Preparing HttpRequest for Stripe API call");
        HttpHeaders headers = new HttpHeaders();
        // Set Basic Auth with secret key (use empty string for password)
        headers.setBasicAuth(
                StripeSecretKey,
                Constant.EMPTY_STRING);

        // Set content type to form-urlencoded as required by Stripe API
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Create a MultiValueMap to hold form data (allows multiple values per key)
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        for (int i = 0; i < CreatePaymentRequest.getLineItems().size(); i++) {
            formData.add("line_items[" + i + "][price_data][currency]",
                    CreatePaymentRequest.getLineItems().get(i).getCurrency());
            formData.add("line_items[" + i + "][price_data][unit_amount]",
                    String.valueOf(CreatePaymentRequest.getLineItems().get(i).getUnitAmount()));
            formData.add("line_items[" + i + "][price_data][product_data][name]",
                    CreatePaymentRequest.getLineItems().get(i).getProductName());
            formData.add("line_items[" + i + "][quantity]",
                    String.valueOf(CreatePaymentRequest.getLineItems().get(i).getQuantity()));
        }
        // Set checkout session mode to payment
        formData.add(Constant.MODE, Constant.MODE_PAYMENT);
        // Add redirect URLs for success and cancel scenarios
        formData.add(Constant.SUCCESS_URL, CreatePaymentRequest.getSuccessUrl());
        formData.add(Constant.CANCEL_URL, CreatePaymentRequest.getCancelUrl());

        HttpRequestData requestData = new HttpRequestData();
        requestData.setHttpMethod(HttpMethod.POST);
        requestData.setUrl(StripeCreateSessionURL);
        requestData.setHttpHeaders(headers);
        requestData.setRequestBody(formData);

        log.info("=> Prepared HttpRequestData: {}", requestData);
        return requestData;
    }

    public Stripe_Response ProcessResponse(ResponseEntity<String> httpResponse) {

        if (httpResponse.getStatusCode().is2xxSuccessful()) {
            log.info("=> HTTP response is successful");

            Stripe_Response response = jsonUtil.ConvertJsonToJavaObject(
                    httpResponse.getBody(), Stripe_Response.class);
            log.info("=> Converted PaymentResponse");

            if (response != null && response.getId() != null && response.getUrl() != null) {
                log.info("=> PaymentResponse is valid and contains necessary fields");
                return response;
            }
        }

        // if we reach this code, means it error & we should throw an exception.
        // if 4xx or 5xx, then also throw Payment creation failed exception
        if (httpResponse.getStatusCode().is4xxClientError()
                || httpResponse.getStatusCode().is5xxServerError()) {
            log.error("=> HTTP response indicates client or server error: {}", httpResponse.getStatusCode());

            // what to pass in errorCode & errorMessage

            // TODO, integrate with some AI model, give the errorJson to AI model, and ask for 1 liner errorDescription.
            // for now passing errorMessage field

            String errorMessage = prepareErrorSummaryMessage(httpResponse);
            log.error("Prepared error message from AI model: {}", errorMessage);

            throw new Error_Response(
                    ErrorCode.STRIPE_ERROR.getErrorCode(),
                    errorMessage,
                    HttpStatus.valueOf(httpResponse.getStatusCode().value())
            );
        }

        log.error("=> Unexpected HTTP response status: {}", httpResponse.getStatusCode());
        throw new Error_Response(
                ErrorCode.PAYMENT_CREATION_FAILED.getErrorCode(),
                ErrorCode.PAYMENT_CREATION_FAILED.getErrorMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }


    private String prepareErrorSummaryMessage(ResponseEntity<String> httpResponse) {
        // TODO currently just returning body, to make code work without Llama setup.
        /*if (true) {
            return httpResponse.getBody();
        }*/

        String promptTemplate = """
                Given the following json message from a third-party API, read the entire JSON, and summarize in 1 line:
                Instructions:
                1. Put a short, simple summary. Which exactly represents what error happened.
                2. Max length of summary less than 200 characters.
                3. Keep the output clear and concise.
                4. Summarize as message that we can send in API response to the client.
                5. Dont point any info to read external documentation or link.
                6. Dont write in double quotes.
                {error_json}
                """;

        String errorJson = httpResponse.getBody();

        String response = chatClient.prompt()
                .system("You are an technical analyst. which just retunrs 1 line summary of the json error")
                .user(promptUserSpec -> promptUserSpec
                        .text(promptTemplate)
                        .param("error_json", errorJson))
                .call()
                .content();

        log.info("=> AI Model response: {}", response);

        return response;
    }
}
