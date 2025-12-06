package com.HulkHire_Tech.Payment.Controllers;

import com.HulkHire_Tech.Payment.Constants.Constant;
import com.HulkHire_Tech.Payment.Pojo.Create_Payment_Request;
import com.HulkHire_Tech.Payment.Responce_Pojo.Payment_Response;
import com.HulkHire_Tech.Payment.Services.Payment_Services;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(Constant.PAYMENTS)
@RequiredArgsConstructor
public class Payment_Controller {

    private final Payment_Services Payment_Services;

    @PostMapping // Postman link: http://localhost:8083/payments
    public Payment_Response Checkout_Session(@RequestBody Create_Payment_Request CreatePaymentRequest) {
        log.info("=> Create Payment Request: {}", CreatePaymentRequest);
        Payment_Response responce = Payment_Services.CreatePayment(CreatePaymentRequest);
        return responce;
    }
    // Get Checkout Session
    @GetMapping("/{id}") // Postman link: http://localhost:8083/payments/{id}
    public Payment_Response Retrieve_Checkout_Session(@PathVariable String id) {
        Payment_Response responce = Payment_Services.RetrivePayment(id);
        log.info("=> Retrieving payment details for id: " + id);
        return responce;
    }

    @PostMapping(Constant.EXPIRE + "/{id}") // Postman link: http://localhost:8083/payments/expire/{id}
    public Payment_Response Expire_Checkout_Session(@PathVariable String id) {
        Payment_Response responce = Payment_Services.ExpirePayment(id);
        log.info("=> Retrieving payment details for id: " + id);
        return responce;
    }

    @PostMapping("/{id}")
    public String Update_Checkout_Session(@RequestBody Create_Payment_Request CreatePaymentRequest, @PathVariable String id) {
        log.info("=> Update Payment Request: {}", CreatePaymentRequest);
        return "Getting payment details for id: " + id;
    }
}
