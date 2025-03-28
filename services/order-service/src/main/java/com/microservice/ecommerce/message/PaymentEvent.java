package com.microservice.ecommerce.message;

import com.microservice.ecommerce.constant.PaymentMethod;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    28/03/2025 at 11:56 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record PaymentEvent(
        UUID orderId,
        PaymentMethod paymentMethod,
        String orderStatus
) {
}

