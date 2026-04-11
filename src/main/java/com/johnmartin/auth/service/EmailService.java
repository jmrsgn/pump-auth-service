package com.johnmartin.auth.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.johnmartin.auth.constants.api.ApiErrorConstants;
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

    public void sendVerificationEmail(String to, String link) {
        LoggerUtility.d(clazz, "Execute method: [sendVerificationEmail]");
        if (to == null || StringUtils.isBlank(to)) {
            throw new IllegalArgumentException(ApiErrorConstants.EMAIL_IS_REQUIRED);
        }

        if (link == null || link.isBlank()) {
            throw new IllegalArgumentException(ApiErrorConstants.VERIFICATION_LINK_IS_REQUIRED);
        }

        try {
            Context context = new Context();
            context.setVariable(ViewAttributes.VERIFICATION_LINK, link);

            String htmlContent = springTemplateEngine.process(ViewAttributes.Template.VERIFICATION_EMAIL, context);
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(SUBJECT);
            helper.setText(htmlContent, true); // true = HTML

            javaMailSender.send(message);
        } catch (Exception ex) {
            throw new RuntimeException(ApiErrorConstants.FAILED_TO_SEND_EMAIL, ex);
        }
    }
}
