package com.dikann.webservice.enums;

import java.util.Arrays;

public enum DefaultRoles {
    ADMIN("ADMIN"),
    USER("USER");

    private String value;

    DefaultRoles(String value) {
        this.value = value;
    }

    public static DefaultRoles fromValue(String value) {
        for (DefaultRoles item : values()) {
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
