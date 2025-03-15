package com.microservice.ecommerce.document;

import com.microservice.ecommerce.message.OrderConfirmation;
import com.microservice.ecommerce.message.PaymentConfirmation;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    15/03/2025 at 5:27 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notification {
    @Id
    String id;

    NotificationType notificationType;

    LocalDateTime notificationDate;

    OrderConfirmation orderConfirmation;

    PaymentConfirmation paymentConfirmation;
}
