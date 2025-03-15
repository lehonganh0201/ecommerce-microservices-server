package com.microservice.ecommerce.message;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    15/03/2025 at 4:57 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record ProductPriceResponse(
        UUID variantId,
        String productName,
        Integer quantity,
        double price
) {
}

