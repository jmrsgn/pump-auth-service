package com.johnmartin.auth.security.filter;

import java.io.IOException;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import com.johnmartin.auth.constants.SecurityConstants;
import com.johnmartin.auth.utilities.LoggerUtility;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;

public class CorrelationIdFilter extends OncePerRequestFilter {

    private static final Class<CorrelationIdFilter> clazz = CorrelationIdFilter.class;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String requestId = request.getHeader(SecurityConstants.HttpHeaders.REQUEST_ID);

        if (StringUtils.isBlank(requestId)) {
            requestId = UUID.randomUUID().toString();
        }

        LoggerUtility.d(clazz, String.format("requestId: [%s]", requestId));

        MDC.put(SecurityConstants.HttpHeaders.REQUEST_ID, requestId);
        request.setAttribute(SecurityConstants.HttpHeaders.REQUEST_ID, requestId);
        response.setHeader(SecurityConstants.HttpHeaders.REQUEST_ID, requestId);

        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(SecurityConstants.HttpHeaders.REQUEST_ID);
        }
    }
}
