package com.microservice.ecommerce.message;

import com.microservice.ecommerce.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    28/03/2025 at 12:08 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class PaymentConsumer {
    OrderService orderService;

    @KafkaListener(topics = "change-order-topic")
    public void consumeChangeOrderTopic(PaymentEvent paymentEvent) {
        orderService.changeOrderStatus(paymentEvent.orderId(), paymentEvent.orderStatus());
    }
}
