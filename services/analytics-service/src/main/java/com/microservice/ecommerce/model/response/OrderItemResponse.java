package com.microservice.ecommerce.model.response;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    23/03/2025 at 11:10 PM
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

