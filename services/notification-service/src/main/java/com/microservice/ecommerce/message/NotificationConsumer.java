package com.microservice.ecommerce.message;

import com.microservice.ecommerce.document.Notification;
import com.microservice.ecommerce.document.NotificationType;
import com.microservice.ecommerce.service.EmailService;
import com.microservice.ecommerce.service.NotificationService;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    15/03/2025 at 5:42 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class NotificationConsumer {
    NotificationService notificationService;
    EmailService emailService;


    @KafkaListener(topics = "payment-success-topic")
    public void consumePaymentSuccess(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("Received success payment message: {}", paymentConfirmation);

        notificationService.saveNotification(Notification.builder()
                .notificationType(NotificationType.PAYMENT_CONFIRMATION)
                .notificationDate(LocalDateTime.now())
                .paymentConfirmation(paymentConfirmation)
                .build());

        emailService.sendEmail(
                paymentConfirmation.email(),
                paymentConfirmation.name(),
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference(),
                null,
                NotificationType.PAYMENT_CONFIRMATION
        );
    }

    @KafkaListener(topics = "payment-fail-topic")
    public void consumePaymentFail(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("Received failed payment message: {}", paymentConfirmation);

        notificationService.saveNotification(Notification.builder()
                .notificationType(NotificationType.PAYMENT_FAIL)
                .notificationDate(LocalDateTime.now())
                .paymentConfirmation(paymentConfirmation)
                .build());

        emailService.sendEmail(
                paymentConfirmation.email(),
                paymentConfirmation.name(),
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference(),
                null,
                NotificationType.PAYMENT_FAIL
        );
    }


    @KafkaListener(topics = "order-confirmation-topic")
    public void consumeNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info(String.format("Consuming the message from order topic Topic:: %s", orderConfirmation));

        notificationService.saveNotification(Notification.builder()
                .notificationType(NotificationType.PAYMENT_CONFIRMATION)
                .notificationDate(LocalDateTime.now())
                .orderConfirmation(orderConfirmation)
                .build());

        emailService.sendEmail(
                orderConfirmation.email(),
                orderConfirmation.fullName(),
                orderConfirmation.totalAmount(),
                orderConfirmation.reference(),
                orderConfirmation.products(),
                NotificationType.ORDER_CONFIRMATION
        );
    }
}
