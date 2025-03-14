package com.microservice.ecommerce.message;

import com.microservice.ecommerce.model.dto.request.OrderItemRequest;
import com.microservice.ecommerce.model.dto.request.PaymentRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    13/03/2025 at 9:09 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class OrderProducer {
    KafkaTemplate<String, Object> kafkaTemplate;

    public void sendUpdateStock(List<OrderItemRequest> requests) {
        Message<List<OrderItemRequest>> message = MessageBuilder
                .withPayload(requests)
                .setHeader(KafkaHeaders.TOPIC, "update-stock-topic")
                .build();

        kafkaTemplate.send(message);
        log.info("Gửi sự kiện cập nhật stock: {}", requests);
    }

//    public void sendSaveMoMoPayment(PaymentRequest request) {
//        Message<PaymentRequest> message = MessageBuilder
//                .withPayload(request)
//                .setHeader(KafkaHeaders.TOPIC, "save-momo-payment-topic")
//                .build();
//
//        kafkaTemplate.send(message);
//    }
//
//    public void sendSaveVNPayPayment(PaymentRequest request) {
//        Message<PaymentRequest> message = MessageBuilder
//                .withPayload(request)
//                .setHeader(KafkaHeaders.TOPIC, "save-vnpay-payment-topic")
//                .build();
//
//        kafkaTemplate.send(message);
//    }
//
//    public void sendCOBPayment(PaymentRequest request) {
//        Message<PaymentRequest> message = MessageBuilder
//                .withPayload(request)
//                .setHeader(KafkaHeaders.TOPIC, "save-cob-payment-topic")
//                .build();
//
//        kafkaTemplate.send(message);
//    }
}
