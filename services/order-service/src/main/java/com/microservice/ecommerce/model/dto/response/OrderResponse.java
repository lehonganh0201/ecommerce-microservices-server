package com.microservice.ecommerce.model.dto.response;

import com.microservice.ecommerce.constant.OrderStatus;
import com.microservice.ecommerce.constant.PaymentMethod;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    09/03/2025 at 5:40 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record OrderResponse(
        UUID id,
        String reference,
        OrderStatus status,
        PaymentMethod paymentMethod,
        double totalAmount,
        List<OrderItemResponse> items,
        PaymentResponse payment,
        LocalDateTime createdDate
) {
}
