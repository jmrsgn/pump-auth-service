package com.johnmartin.auth.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.johnmartin.auth.constants.api.ApiConstants;
import com.johnmartin.auth.constants.api.ApiErrorConstants;
import com.johnmartin.auth.events.AuthUserCreatedEvent;
import com.johnmartin.auth.service.EmailService;
import com.johnmartin.auth.service.VerificationTokenService;
import com.johnmartin.auth.utilities.LoggerUtility;

@Component
public class AuthUserCreatedListener {

    private static final Class<AuthUserCreatedListener> clazz = AuthUserCreatedListener.class;
    private final VerificationTokenService verificationTokenService;
    private final EmailService emailService;

    public AuthUserCreatedListener(VerificationTokenService verificationTokenService, EmailService emailService) {
        this.verificationTokenService = verificationTokenService;
        this.emailService = emailService;
    }

    @Async
    @EventListener
    public void handleUserRegistered(AuthUserCreatedEvent event) {
        // This method is automatically called by Spring using the event publisher
        LoggerUtility.d(clazz, "Execute method: [handleUserRegistered]");
        if (event == null || event.userId() == null || event.email() == null) {
            throw new IllegalArgumentException(ApiErrorConstants.INVALID_EVENT_DATA);
        }

        String token = verificationTokenService.generateToken(event.userId());
        String link = ApiConstants.BASE_URL + ApiConstants.Path.API_AUTH + ApiConstants.Path.VERIFY + "?token=" + token;
        emailService.sendVerificationEmail(event.email(), link);
    }
}
