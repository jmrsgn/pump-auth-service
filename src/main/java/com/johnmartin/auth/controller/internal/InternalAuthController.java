package com.johnmartin.auth.controller.internal;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johnmartin.auth.constants.SecurityConstants;
import com.johnmartin.auth.constants.api.ApiConstants;
import com.johnmartin.auth.dto.response.Result;
import com.johnmartin.auth.dto.response.UserResponse;
import com.johnmartin.auth.entity.UserEntity;
import com.johnmartin.auth.mapper.UserMapper;
import com.johnmartin.auth.security.JwtUtil;
import com.johnmartin.auth.service.UserService;
import com.johnmartin.auth.utilities.LoggerUtility;

@RestController
@RequestMapping(ApiConstants.InternalPath.API_AUTH_INTERNAL)
public class InternalAuthController {

    private static final Class<InternalAuthController> clazz = InternalAuthController.class;

    private final JwtUtil jwtUtil;

    private final UserService userService;

    public InternalAuthController(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping(ApiConstants.InternalPath.VALIDATE)
    public ResponseEntity<Result<UserResponse>> validateToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                                              @RequestHeader(value = SecurityConstants.REQUEST_ID, required = false) String requestId) {
        LoggerUtility.d(clazz, "Execute method: [validateToken]");
        if (StringUtils.isBlank(requestId)) {
            requestId = UUID.randomUUID().toString();
        }

        LoggerUtility.d(clazz, String.format("requestId: [%s]", requestId));
        MDC.put(SecurityConstants.REQUEST_ID, requestId);

        try {
            String token = authorizationHeader.replace("Bearer ", StringUtils.EMPTY);
            String userId = jwtUtil.extractUserId(token);
            UserEntity user = userService.findById(UUID.fromString(userId));
            return ResponseEntity.ok(Result.success(UserMapper.toResponse(user)));
        } finally {
            MDC.remove(SecurityConstants.REQUEST_ID);
        }
    }

}
