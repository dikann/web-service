package com.dikann.webservice.convertor;

import com.dikann.webservice.enums.SortDirEnum;

import java.beans.PropertyEditorSupport;

public class SortDirEnumConverter extends PropertyEditorSupport {

    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
        setValue(SortDirEnum.fromValue(text));
    }
}
