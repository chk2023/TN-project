package com._3dhs.tnproject.common.exceptionhandler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String errorView(Exception e, Model model) {

        e.printStackTrace();

        model.addAttribute("errorMessage", e.getMessage());

        return "/error/common";
    }
}
