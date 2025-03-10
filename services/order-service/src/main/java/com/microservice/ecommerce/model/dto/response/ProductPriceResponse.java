package com.microservice.ecommerce.model.dto.response;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    10/03/2025 at 9:00 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record ProductPriceResponse(
        UUID variantId,
        double price
) {
}
