package com.HulkHire_Tech.PaymentProcessingService.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class Json_Util {
    private final ObjectMapper objectMapper;

    public <T> T ConvertJsonToJavaObject(String responseEntity, Class<T> valueType) {
        try {
            return objectMapper.readValue(responseEntity, valueType);
        } catch (Exception e) {
            log.error("Error parsing JSON response: {}", e.getMessage());
            throw new RuntimeException("Failed to parse JSON response", e);
        }
    }

    public String ConvertJavaObjectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("Error converting Java object to JSON: {}", e.getMessage());
            throw new RuntimeException("Failed to convert Java object to JSON", e);
        }
    }
}
