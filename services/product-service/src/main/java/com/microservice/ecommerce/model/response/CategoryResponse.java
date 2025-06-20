package com.microservice.ecommerce.model.response;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    06/03/2025 at 6:04 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record CategoryResponse(
        UUID id,
        String name,
        String description,
        String imageUrl
) {
}
