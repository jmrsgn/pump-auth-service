package com.johnmartin.auth.security.custom;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johnmartin.auth.constants.api.ApiConstants;
import com.johnmartin.auth.constants.api.ApiErrorMessages;
import com.johnmartin.auth.dto.response.ApiErrorResponse;
import com.johnmartin.auth.dto.response.Result;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException ex) throws IOException {

        ApiErrorResponse error = new ApiErrorResponse(HttpStatus.FORBIDDEN.value(),
                                                      ApiConstants.Error.FORBIDDEN,
                                                      ApiErrorMessages.YOU_ARE_NOT_ALLOWED_TO_ACCESS_THIS_RESOURCE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(Result.failure(error)));
    }
}
