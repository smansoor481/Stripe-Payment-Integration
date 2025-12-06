package com.HulkHire_Tech.Payment.Services;


import com.HulkHire_Tech.Payment.Pojo.Create_Payment_Request;
import com.HulkHire_Tech.Payment.Responce_Pojo.Payment_Response;
import org.springframework.stereotype.Service;

@Service
public interface Payment_Services {
    Payment_Response CreatePayment(Create_Payment_Request CreatePaymentRequest);

    Payment_Response RetrivePayment(String id);

    Payment_Response ExpirePayment(String id);
}
