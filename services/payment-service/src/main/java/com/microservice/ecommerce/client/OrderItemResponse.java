package com.microservice.ecommerce.client;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    24/03/2025 at 5:36 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record OrderItemResponse(
        UUID variantId,
        Integer quantity,
        Double price
) {
}

