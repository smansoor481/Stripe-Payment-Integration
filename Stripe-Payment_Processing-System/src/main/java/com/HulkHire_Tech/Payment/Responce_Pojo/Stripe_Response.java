package com.HulkHire_Tech.Payment.Responce_Pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Stripe_Response {
    private String id;
    private String status;
    private String payment_status;
    private String url;
}
