package com.microservice.ecommerce.service.impl;

import com.microservice.ecommerce.document.NotificationType;
import com.microservice.ecommerce.constant.EmailTemplate;
import com.microservice.ecommerce.message.ProductPriceResponse;
import com.microservice.ecommerce.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    15/03/2025 at 5:06 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class EmailServiceImpl implements EmailService {
    JavaMailSender javaMailSender;
    SpringTemplateEngine templateEngine;

    @Async
    @Override
    public void sendEmail(String destinationEmail, String customerName, Double amount, String orderReference,
                          List<ProductPriceResponse> products, NotificationType notificationType) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        messageHelper.setFrom("contact@lehonganh.com");
        messageHelper.setTo(destinationEmail);

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("amount", amount);
        variables.put("orderReference", orderReference);

        if (notificationType == NotificationType.ORDER_CONFIRMATION) {
            variables.put("products", products);
        }

        EmailTemplate emailTemplate = getEmailTemplate(notificationType);
        messageHelper.setSubject(emailTemplate.getSubject());

        processAndSendEmail(mimeMessage, messageHelper, emailTemplate.getTemplate(), variables);
    }

    private EmailTemplate getEmailTemplate(NotificationType notificationType) {
        switch (notificationType) {
            case ORDER_CONFIRMATION:
                return EmailTemplate.ORDER_CONFIRMATION;
            case PAYMENT_CONFIRMATION:
                return EmailTemplate.PAYMENT_CONFIRMATION;
            case PAYMENT_FAIL:
                return EmailTemplate.PAYMENT_FAIL;
            default:
                throw new IllegalArgumentException("Method not allow.");
        }
    }

    private void processAndSendEmail(MimeMessage mimeMessage, MimeMessageHelper messageHelper,
                                     String template, Map<String, Object> variables) throws MessagingException {
        try {
            Context context = new Context();
            context.setVariables(variables);
            String htmlTemplate = templateEngine.process(template, context);

            messageHelper.setText(htmlTemplate, true);
            javaMailSender.send(mimeMessage);

            log.info("INFO email successfully sent to {}", messageHelper.getMimeMessage().getAllRecipients());
        } catch (Exception ex) {
            log.warn("WARNING - Cannot send email to {}", messageHelper.getMimeMessage().getAllRecipients(), ex);
        }
    }
}
