package com.johnmartin.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.johnmartin.auth.constants.api.ApiConstants;
import com.johnmartin.auth.constants.view.ViewAttributes;
import com.johnmartin.auth.enums.VerificationStatus;
import com.johnmartin.auth.service.AuthService;
import com.johnmartin.auth.service.VerificationTokenService;

@Controller
@RequestMapping(ApiConstants.Path.API_AUTH)
public class AuthViewController {

    private final AuthService authService;
    private final VerificationTokenService verificationTokenService;

    public AuthViewController(AuthService authService, VerificationTokenService verificationTokenService) {
        this.authService = authService;
        this.verificationTokenService = verificationTokenService;
    }

    /**
     * Verify account endpoint
     * 
     * @param token
     *            - Generated UUID
     * @param model
     *            - Thymeleaf model container data for HTML
     * @return template
     */
    @GetMapping(ApiConstants.Path.VERIFY)
    public String verify(@RequestParam String token, Model model) {
        VerificationStatus status = verificationTokenService.verifyToken(token);
        // Add attributes to the model based on the verification status result
        authService.setModelData(model, status);
        return ViewAttributes.Template.VERIFICATION_RESULT;
    }
}
