package com.dikann.webservice.utils;

import com.dikann.webservice.enums.SortByEnum;

import java.beans.PropertyEditorSupport;

public class SortByEnumConvertor extends PropertyEditorSupport {
    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
        setValue(SortByEnum.fromValue(text));
    }
}
