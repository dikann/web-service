package com.dikann.webservice.utils;


import com.dikann.webservice.convertor.*;
import com.dikann.webservice.enums.*;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class GlobalInitBinder {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(SortDirEnum.class, new SortDirEnumConverter());
        binder.registerCustomEditor(SortByEnum.class, new SortByEnumConvertor());
        binder.registerCustomEditor(SortByProductEnum.class, new SortByProductEnumConvertor());
        binder.registerCustomEditor(SortByUserEnum.class, new SortByUserEnumConvertor());
        binder.registerCustomEditor(SortByCartItemEnum.class, new SortByCartItemConvertor());
    }
}
