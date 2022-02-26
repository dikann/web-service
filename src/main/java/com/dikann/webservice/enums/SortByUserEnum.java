package com.dikann.webservice.enums;

import java.util.Arrays;

public enum SortByUserEnum {
    ID("id"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    CREATED_DATE("createdDate"),
    MODIFIED_DATE("modifiedDate");

    private String value;

    SortByUserEnum(String value) {
        this.value = value;
    }

    public static SortByUserEnum fromValue(String value) {
        for (SortByUserEnum item : values()) {
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
