package com.microservice.ecommerce.client;

import java.util.List;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    16/03/2025 at 2:45 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record ProductResponse(
        UUID id,

        String productName,

        Integer stock,

        Double price,

        List<ProductAttribute>attributes,

        String imageUrl
) {
}
