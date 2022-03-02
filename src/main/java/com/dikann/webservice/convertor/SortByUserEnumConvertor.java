package com.dikann.webservice.convertor;

import com.dikann.webservice.enums.SortByUserEnum;

import java.beans.PropertyEditorSupport;

public class SortByUserEnumConvertor extends PropertyEditorSupport {
    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
        setValue(SortByUserEnum.fromValue(text));
    }
}
