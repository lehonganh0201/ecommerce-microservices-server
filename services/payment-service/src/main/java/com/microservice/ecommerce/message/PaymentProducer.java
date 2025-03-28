package com.microservice.ecommerce.message;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    15/03/2025 at 11:56 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class PaymentProducer {
    KafkaTemplate<String, Object> kafkaTemplate;

    public void sendPaymentConfirmation(PaymentConfirmation paymentConfirmation) {
        Message<PaymentConfirmation> message = MessageBuilder
                .withPayload(paymentConfirmation)
                .setHeader(KafkaHeaders.TOPIC, "payment-success-topic")
                .build();

        kafkaTemplate.send(message);
        log.info("Gửi sự kiện thanh toán thành công");
    }

    public void sendPaymentFail(PaymentConfirmation paymentConfirmation) {
        Message<PaymentConfirmation> message = MessageBuilder
                .withPayload(paymentConfirmation)
                .setHeader(KafkaHeaders.TOPIC, "payment-fail-topic")
                .build();

        kafkaTemplate.send(message);
        log.info("Gửi sự kiện thanh toán thất bại");
    }

    public void sendOrderCallBack(PaymentEvent paymentEvent) {
        Message<PaymentEvent> message = MessageBuilder
                .withPayload(paymentEvent)
                .setHeader(KafkaHeaders.TOPIC, "change-order-topic")
                .build();

        kafkaTemplate.send(message);
        log.info("Gửi sự kiện thanh toán thành công");
    }
}
