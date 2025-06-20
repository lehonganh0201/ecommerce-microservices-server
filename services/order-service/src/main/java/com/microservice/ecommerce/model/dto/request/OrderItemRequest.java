package com.microservice.ecommerce.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    09/03/2025 at 5:49 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record OrderItemRequest(
        @NotNull(message = "Product Variant ID cannot be null")
        UUID variantId,

        @Min(value = 1, message = "Quantity must be at least 1")
        @NotNull(message = "Quantity is required")
        Integer quantity
) {
}
