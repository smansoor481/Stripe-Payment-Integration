package com.HulkHire_Tech.PaymentProcessingService.Http;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@Data
@Builder
public class HttpRequestData {
    private HttpMethod httpMethod;
    private String url;
    private HttpHeaders httpHeaders;
    private Object requestBody;
}
