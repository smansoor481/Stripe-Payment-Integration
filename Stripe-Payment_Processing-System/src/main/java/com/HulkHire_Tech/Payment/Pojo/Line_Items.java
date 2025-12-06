package com.HulkHire_Tech.Payment.Pojo;

import lombok.Data;

@Data
public class Line_Items {
    private String currency;
    private int quantity;
    private String productName;
    private int unitAmount;
}
