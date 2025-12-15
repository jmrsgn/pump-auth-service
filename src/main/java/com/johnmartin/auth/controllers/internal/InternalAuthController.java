package com.johnmartin.auth.controllers.internal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johnmartin.auth.constants.api.ApiConstants;
import com.johnmartin.auth.dto.response.UserResponse;
import com.johnmartin.auth.entities.UserEntity;
import com.johnmartin.auth.mapper.UserMapper;
import com.johnmartin.auth.security.JwtUtil;

@RestController
@RequestMapping(ApiConstants.Path.API_AUTH_INTERNAL)
public class InternalAuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(ApiConstants.Path.VALIDATE)
    public ResponseEntity<UserResponse> validateToken(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", StringUtils.EMPTY);
        UserEntity user = jwtUtil.validateTokenAndGetUser(token);
        return ResponseEntity.ok(UserMapper.toResponse(user));
    }

}
