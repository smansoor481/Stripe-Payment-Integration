package com.HulkHire_Tech.PaymentProcessingService.Services.Interface;

import com.HulkHire_Tech.PaymentProcessingService.Notification.Notification_Request;
import org.springframework.stereotype.Service;

@Service
public interface Notification_Service {
    void ProcessNotification(Notification_Request notificationRequest);
}
