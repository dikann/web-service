package com.dikann.webservice.utils;

import com.dikann.webservice.enums.SortByProductEnum;

import java.beans.PropertyEditorSupport;

public class SortByProductEnumConvertor extends PropertyEditorSupport {
    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
        setValue(SortByProductEnum.fromValue(text));
    }
}