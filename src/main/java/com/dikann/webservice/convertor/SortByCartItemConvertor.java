package com.dikann.webservice.convertor;

import com.dikann.webservice.enums.SortByCartItemEnum;

import java.beans.PropertyEditorSupport;

public class SortByCartItemConvertor  extends PropertyEditorSupport {
    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
        setValue(SortByCartItemEnum.fromValue(text));
    }
}
