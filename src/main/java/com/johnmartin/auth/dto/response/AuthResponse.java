package com.johnmartin.auth.dto.response;

import com.johnmartin.auth.dto.response.internal.SocialUserResponse;

public record AuthResponse(String accessToken, SocialUserResponse socialUserResponse, Long expiresIn) {
}
