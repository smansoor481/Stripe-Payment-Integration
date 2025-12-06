package com.HulkHire_Tech.Payment.Pojo;

import lombok.Data;

import java.util.List;

@Data
public class Create_Payment_Request {
    private String successUrl;
    private String cancelUrl;

    private List<Line_Items> lineItems;
}
