package com.microservice.ecommerce.model.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    24/03/2025 at 1:06 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record ReviewResponse(
        UUID id,
        UUID productId,
        String fullName,
        Integer rating,
        String comment,
        LocalDateTime createDate
) {
}
