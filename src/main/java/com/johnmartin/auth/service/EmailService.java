package com.johnmartin.auth.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.johnmartin.auth.constants.api.messages.ApiMessages;
import com.johnmartin.auth.utilities.LoggerUtility;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private static final Class<EmailService> clazz = EmailService.class;
    private final static String SUBJECT = "Verify your account";

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine springTemplateEngine;

    public EmailService(JavaMailSender javaMailSender, SpringTemplateEngine springTemplateEngine) {
        this.javaMailSender = javaMailSender;
        this.springTemplateEngine = springTemplateEngine;
    }

    public void sendVerificationEmail(String to, String link) {
        LoggerUtility.d(clazz, "Execute method: [sendVerificationEmail]");
        if (to == null || StringUtils.isBlank(to)) {
            throw new IllegalArgumentException(ApiMessages.EMAIL_IS_REQUIRED);
        }

        if (link == null || link.isBlank()) {
            throw new IllegalArgumentException(ApiMessages.VERIFICATION_LINK_IS_REQUIRED);
        }

        try {
            Context context = new Context();
            context.setVariable("verificationLink", link);

            String htmlContent = springTemplateEngine.process("verification-email", context);
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(SUBJECT);
            helper.setText(htmlContent, true); // true = HTML

            javaMailSender.send(message);
        } catch (Exception ex) {
            throw new RuntimeException(ApiMessages.FAILED_TO_SEND_EMAIL, ex);
        }
    }
}
