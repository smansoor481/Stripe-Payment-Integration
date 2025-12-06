package com.HulkHire_Tech.PaymentProcessingService.Constant;

import lombok.Getter;

@Getter
public enum PaymentMethodEnums {
    APM(1, "APM");

    private final int id;
    private final String name;

    PaymentMethodEnums(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static PaymentMethodEnums fromId(int id) {
        for (PaymentMethodEnums e : values()) {
            if (e.id == id) return e;
        }
        return null;
    }
    public static PaymentMethodEnums fromName(String name) {
        for (PaymentMethodEnums e : values()) {
            if (e.name.equalsIgnoreCase(name)) return e;
        }
        return null;
    }
}
