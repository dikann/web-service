package com.dikann.webservice.utils;


import com.dikann.webservice.enums.SortByEnum;
import com.dikann.webservice.enums.SortByProductEnum;
import com.dikann.webservice.enums.SortDirEnum;
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
    }
}
