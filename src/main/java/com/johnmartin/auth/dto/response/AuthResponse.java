package com.johnmartin.auth.dto.response;

public record AuthResponse(String accessToken, SocialUserResponse socialUserResponse, Long expiresIn) {
}
