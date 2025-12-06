package com.HulkHire_Tech.PaymentProcessingService.Transection.TransectionDAO_Repository;

import com.HulkHire_Tech.PaymentProcessingService.Transection.Transection_Entity;

public interface TransectionDAO_Repository {
    public Integer InsertTransection(Transection_Entity transection_entity);
    public Transection_Entity getTransectionByTransectionReference(String trxReference);
    public Transection_Entity getTransectionByProviderReference(String providerReference, int providerId);
    public Integer updateTransectionByTransectionReference(Transection_Entity transection_entity);
    public Integer PendingTransectionByTransectionReference(Transection_Entity transection_entity);

}
