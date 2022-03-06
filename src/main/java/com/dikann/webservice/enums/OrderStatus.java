package com.dikann.webservice.enums;

import java.util.Arrays;

public enum OrderStatus {
    CANCELED("canceled"),
    DRAFT("draft"),
    PENDING("pending"),
    PROCESSING("processing"),
    COMPLETED("completed"),
    REJECTED("rejected");

    private String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public static OrderStatus fromValue(String value) {
        for (OrderStatus item : values()) {
            if (item.value.equalsIgnoreCase(value)) {
                return item;
            }
        }
        throw new IllegalArgumentException(
                "Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
    }

    @Override
    public String toString() {
        return this.value;
    }
}
