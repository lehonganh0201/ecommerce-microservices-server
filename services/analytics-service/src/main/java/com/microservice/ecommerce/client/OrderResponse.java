package com.microservice.ecommerce.client;

import com.microservice.ecommerce.constant.OrderStatus;
import com.microservice.ecommerce.constant.PaymentMethod;
import com.microservice.ecommerce.model.response.OrderItemResponse;
import com.microservice.ecommerce.model.response.PaymentResponse;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    23/03/2025 at 11:08 PM
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
