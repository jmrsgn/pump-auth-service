package com.johnmartin.auth.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HtmlExceptionHandler {

    @ExceptionHandler({ BadRequestException.class, NotFoundException.class })
    public String handleVerificationErrors(Exception ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "verification-error";
    }
}
