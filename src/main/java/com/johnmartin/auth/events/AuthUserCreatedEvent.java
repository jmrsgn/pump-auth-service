package com.johnmartin.auth.events;

import java.util.UUID;

public record AuthUserCreatedEvent(UUID userId, String email) {
}
