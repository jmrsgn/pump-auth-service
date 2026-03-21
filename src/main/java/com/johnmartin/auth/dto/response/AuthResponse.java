package com.johnmartin.auth.dto.response;

public record AuthResponse(String token, UserResponse userResponse) {
}
