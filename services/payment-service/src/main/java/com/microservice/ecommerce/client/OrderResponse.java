package com.microservice.ecommerce.client;

import com.microservice.ecommerce.constant.OrderStatus;
import com.microservice.ecommerce.constant.PaymentMethod;
import com.microservice.ecommerce.model.dto.response.PaymentResponse;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    24/03/2025 at 5:34 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record OrderResponse(
        String reference,
        OrderStatus status,
        PaymentMethod paymentMethod,
        double totalAmount,
        List<OrderItemResponse> items,
        PaymentResponse payment,
        LocalDateTime createdDate
) {
}
