package com.bootx.controller;

import com.bootx.common.DateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.Date;

/**
 * @author blackboy1987
 */
@ControllerAdvice("com.bootx.controller")
public class BaseContrllerAdvice {

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class,new DateEditor(true));
    }
}
