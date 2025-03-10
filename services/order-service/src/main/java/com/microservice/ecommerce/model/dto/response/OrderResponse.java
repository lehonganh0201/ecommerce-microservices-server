package com.microservice.ecommerce.model.dto.response;

import com.microservice.ecommerce.constant.OrderStatus;
import com.microservice.ecommerce.constant.PaymentMethod;

import java.util.List;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    09/03/2025 at 5:40 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record OrderResponse(
        String reference,
        OrderStatus status,
        PaymentMethod paymentMethod,
        double totalAmount,
        List<OrderItemResponse> items
) {
}
