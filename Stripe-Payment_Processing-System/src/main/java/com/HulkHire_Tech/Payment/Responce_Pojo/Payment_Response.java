package com.HulkHire_Tech.Payment.Responce_Pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Payment_Response {
    private String id; // Session ID
    private String sessionStatus; // Create, Expire, Retrieve
    private String paymentStatus; // Paid Unpaid Expired
    private String url; // Redirect URL
}