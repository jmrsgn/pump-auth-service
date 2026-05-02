package com.johnmartin.auth.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.johnmartin.auth.constants.error.ExternalServiceErrorConstants;
import com.johnmartin.auth.constants.error.ValidationErrorConstants;
import com.johnmartin.auth.constants.view.ViewAttributes;
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

    public void sendVerificationEmail(String email, String link) {
        LoggerUtility.d(clazz, "Execute method: [sendVerificationEmail]");
        if (email == null || StringUtils.isBlank(email)) {
            throw new IllegalArgumentException(ValidationErrorConstants.EMAIL_IS_REQUIRED);
        }

        if (link == null || link.isBlank()) {
            throw new IllegalArgumentException(ValidationErrorConstants.VERIFICATION_LINK_IS_REQUIRED);
        }

        try {
            Context context = new Context();
            context.setVariable(ViewAttributes.VERIFICATION_LINK, link);

            String htmlContent = springTemplateEngine.process(ViewAttributes.HtmlTemplate.VERIFICATION_EMAIL, context);
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject(SUBJECT);
            helper.setText(htmlContent, true); // true = HTML

            javaMailSender.send(message);
        } catch (Exception ex) {
            throw new RuntimeException(ExternalServiceErrorConstants.FAILED_TO_SEND_EMAIL, ex);
        }
    }
}
