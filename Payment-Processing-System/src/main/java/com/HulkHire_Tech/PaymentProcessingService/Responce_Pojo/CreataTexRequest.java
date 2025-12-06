package com.HulkHire_Tech.PaymentProcessingService.Responce_Pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreataTexRequest {
    private Integer userId;     // incoming request
    private String paymentMethod;    //1, 'APM' incoming request
    private String provider;     //1, 'STRIPE' incoming request
    private String paymentType;  //1, 'SALE' incoming request
    private BigDecimal amount;  //incoming request
    private String currency;    // incoming request
    private String merchantTransactionReference;    // incoming request

}
/* josn data
{
  "userId": 101,
  "paymentMethod": "APM",
  "provider": "STRIPE",
  "paymentType": "SALE",
  "amount": 5.75,
  "currency": "EUR",
  "merchantTransactionReference": "ORD-20250903-XYZ"
}
*/
