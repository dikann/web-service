package com.dikann.webservice.convertor;

import com.dikann.webservice.enums.OrderStatus;

import java.beans.PropertyEditorSupport;

public class OrderStatusConvertor extends PropertyEditorSupport {
    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
        setValue(OrderStatus.fromValue(text));
    }
}



