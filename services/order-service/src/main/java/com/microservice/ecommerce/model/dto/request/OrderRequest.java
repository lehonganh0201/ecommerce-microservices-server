package com.microservice.ecommerce.model.dto.request;

import com.microservice.ecommerce.constant.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    09/03/2025 at 5:41 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record OrderRequest(
        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod,

        String language,

        String bankCode,

        @NotEmpty(message = "Order must contain at least one item")
        @Valid
        List<OrderItemRequest> items
) {
}
