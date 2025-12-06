package com.HulkHire_Tech.PaymentProcessingService.Constant;

public enum NotificationType {
        PAYMENT_SUCCESS(1, "PAYMENT_SUCCESS"),
        PAYMENT_FAILED(2, "PAYMENT_FAILED");

        private final int id;
        private final String name;

        NotificationType(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        // Get enum by id
        public static NotificationType fromID(int id) {
            for (NotificationType status : values()) {
                if (status.id == id) {
                    return status;
                }
            }
            return null; // or throw an IllegalArgumentException
        }

        // Get enum by name
        public static NotificationType fromName(String name) {
            for (NotificationType status : values()) {
                if (status.name.equalsIgnoreCase(name)) {
                    return status;
                }
            }
            return null; // or throw an IllegalArgumentException
        }
    }
