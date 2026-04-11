package com.johnmartin.auth.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.johnmartin.auth.constants.view.ViewAttributes;

@ControllerAdvice
public class HtmlExceptionHandler {

    @ExceptionHandler({ BadRequestException.class, NotFoundException.class })
    public String handleVerificationErrors(Exception ex, Model model) {
        model.addAttribute(ViewAttributes.MESSAGE, ex.getMessage());
        return ViewAttributes.Template.VERIFICATION_ERROR;
    }
}
