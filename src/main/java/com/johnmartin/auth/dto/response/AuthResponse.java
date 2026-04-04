package com.johnmartin.auth.dto.response;

public record AuthResponse(String accessToken, UserResponse userResponse, Long expiresIn) {
}
