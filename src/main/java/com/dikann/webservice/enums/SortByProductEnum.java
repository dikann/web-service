package com.dikann.webservice.enums;

import java.util.Arrays;

public enum SortByProductEnum {
    ID("id"),
    NAME("name"),
    SKU("sku"),
    PRICE("price"),
    QUANTITY("quantity"),
    CREATED_DATE("createdDate"),
    MODIFIED_DATE("modifiedDate");

    private String value;

    SortByProductEnum(String value) {
        this.value = value;
    }

    public static SortByProductEnum fromValue(String value) {
        for (SortByProductEnum item : values()) {
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
