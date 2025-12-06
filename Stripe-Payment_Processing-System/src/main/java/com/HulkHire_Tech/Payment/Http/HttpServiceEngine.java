package com.HulkHire_Tech.Payment.Http;

import com.HulkHire_Tech.Payment.Constants.ErrorCode;
import com.HulkHire_Tech.Payment.Pojo.Error_Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class HttpServiceEngine {

    private final RestClient restClient;

    public HttpServiceEngine(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public ResponseEntity<String> MakingHttpCall(HttpRequestData requestData) {
        log.info("=> Making HTTP Call");
        // Make POST request to Stripe API
        try {
            return restClient.method(requestData.getHttpMethod()) // Use HttpMethod from request object
                    // Set the API endpoint URL
                    .uri(requestData.getUrl())
                    // Add headers using lambda expression
                    .headers(httpHeaders -> httpHeaders.addAll(requestData.getHttpHeaders()))
                    // Set the form data as request body
                    .body(requestData.getRequestBody())
                    // Execute the request Convert response to String
                    .retrieve().toEntity(String.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // For 504 Gateway Timeout we need to throw Gateway Timeout exception, For 503 Service Unavailable we need to throw Service Unavailable exception
            if (e.getStatusCode() == HttpStatus.GATEWAY_TIMEOUT) {
                throw new Error_Response(ErrorCode.UNABLE_TO_CONNECT_STRIPE_SERVICE.getErrorCode(),
                        ErrorCode.UNABLE_TO_CONNECT_STRIPE_SERVICE.getErrorMessage(),
                        HttpStatus.GATEWAY_TIMEOUT);
            } else if (e.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
                throw new Error_Response(ErrorCode.UNABLE_TO_CONNECT_STRIPE_SERVICE.getErrorCode(),
                        ErrorCode.UNABLE_TO_CONNECT_STRIPE_SERVICE.getErrorMessage(),
                        HttpStatus.SERVICE_UNAVAILABLE);
            }
            //valid response from stripe but with error status code
            log.error("=> HTTP error response from external service: {}", e.getResponseBodyAsString());
            //Create responseEntity with error body and status code from exception and return the object
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new Error_Response(ErrorCode.UNABLE_TO_CONNECT_STRIPE_SERVICE.getErrorCode(),
                    ErrorCode.UNABLE_TO_CONNECT_STRIPE_SERVICE.getErrorMessage(),
                    HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
