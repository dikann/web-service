package com.dikann.webservice.enums;

import java.util.Arrays;

public enum SortByCartItemEnum {
    ID("id"),
    CREATED_DATE("createdDate"),
    MODIFIED_DATE("modifiedDate");

    private String value;

    SortByCartItemEnum(String value) {
        this.value = value;
    }

    public static SortByCartItemEnum fromValue(String value) {
        for (SortByCartItemEnum item : values()) {
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
