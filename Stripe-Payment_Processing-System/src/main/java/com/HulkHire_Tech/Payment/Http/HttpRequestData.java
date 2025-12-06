package com.HulkHire_Tech.Payment.Http;

import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@Data
public class HttpRequestData {
    private HttpMethod httpMethod; // POST, GET, DELETE, PUT
    private String url; // StripeApiEndpoint
    private HttpHeaders httpHeaders; // Authorization, Content-Type
    private Object requestBody; // FormDate Line items, SuccessUrl, CancelUrl
}
