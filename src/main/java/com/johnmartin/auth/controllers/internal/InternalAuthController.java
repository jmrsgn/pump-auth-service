package com.johnmartin.auth.controllers.internal;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johnmartin.auth.constants.SecurityConstants;
import com.johnmartin.auth.constants.api.ApiConstants;
import com.johnmartin.auth.dto.response.UserResponse;
import com.johnmartin.auth.entities.UserEntity;
import com.johnmartin.auth.mapper.UserMapper;
import com.johnmartin.auth.security.JwtUtil;
import com.johnmartin.auth.utilities.LoggerUtility;

@RestController
@RequestMapping(ApiConstants.Path.API_AUTH_INTERNAL)
public class InternalAuthController {

    private final Class<InternalAuthController> clazz = InternalAuthController.class;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(ApiConstants.Path.VALIDATE)
    public ResponseEntity<UserResponse> validateToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                                      @RequestHeader(value = SecurityConstants.REQUEST_ID, required = false) String requestId) {
        LoggerUtility.d(clazz, "Execute method: [validateToken]");
        if (StringUtils.isBlank(requestId)) {
            requestId = UUID.randomUUID().toString();
        }

        MDC.put(SecurityConstants.REQUEST_ID, requestId);

        try {
            String token = authorizationHeader.replace("Bearer ", StringUtils.EMPTY);
            UserEntity user = jwtUtil.validateTokenAndGetUser(token);
            return ResponseEntity.ok(UserMapper.toResponse(user));
        } finally {
            MDC.remove(SecurityConstants.REQUEST_ID);
        }
    }

}
