package com.HulkHire_Tech.Payment.Helpers;

import com.HulkHire_Tech.Payment.Constants.Constant;
import com.HulkHire_Tech.Payment.Constants.ErrorCode;
import com.HulkHire_Tech.Payment.Http.HttpRequestData;
import com.HulkHire_Tech.Payment.Pojo.Error_Response;
import com.HulkHire_Tech.Payment.Responce_Pojo.Stripe_Response;
import com.HulkHire_Tech.Payment.Util.JSON_Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Expire_Payment_Helper {
    @Value("${stripe.secret.key}")
    private String StripeSecretKey;
    @Value("${stripe.expire.session.url}")
    private String StripeExpireSessionTemplate;

    private final JSON_Util jsonUtil;

    //TODO We need to Give a valid Open AI API key in application.properties file to test this method
    private final ChatClient chatClient;

    public HttpRequestData PrepareHttpRequest(String id) {
        log.info("=> Preparing HttpRequest for Stripe API call");
        HttpHeaders headers = new HttpHeaders();
        // Set Basic Auth with secret key (use empty string for password)
        headers.setBasicAuth(
                StripeSecretKey,
                Constant.EMPTY_STRING);

        String ExpireSessionUrl = StripeExpireSessionTemplate.replace("{id}", id);

        HttpRequestData requestData = new HttpRequestData();
        requestData.setHttpMethod(HttpMethod.POST);
        requestData.setUrl(ExpireSessionUrl);
        requestData.setHttpHeaders(headers);
        requestData.setRequestBody(""); // No body needed for retrieval

        log.info("=> Prepared HttpRequestData: {}", requestData);
        return requestData;
    }

    public Stripe_Response ProcessResponse(ResponseEntity<String> httpResponse) {

        if (httpResponse.getStatusCode().is2xxSuccessful()) {
            log.info("=> HTTP response is successful");

            Stripe_Response response = jsonUtil.ConvertJsonToJavaObject(
                    httpResponse.getBody(), Stripe_Response.class);
            log.info("=> Converted PaymentResponse");

            if (response != null && response.getId() != null && response.getStatus() != null) {
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
            log.error("=> Prepared error message from AI model: {}", errorMessage);

            throw new Error_Response(
                    ErrorCode.STRIPE_ERROR.getErrorCode(),
                    errorMessage,
                    HttpStatus.valueOf(httpResponse.getStatusCode().value())
            );
        }

        log.error("=> Unexpected HTTP response status: {}", httpResponse.getStatusCode());
        throw new Error_Response(
                ErrorCode.EXPIRE_PAYEMTNT_FAILED.getErrorCode(), ErrorCode.EXPIRE_PAYEMTNT_FAILED.getErrorMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }


    private String prepareErrorSummaryMessage(ResponseEntity<String> httpResponse) {
        // TODO currently just returning body, to make code work without Llama setup.
        if(true) {
            return httpResponse.getBody();
        }

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
