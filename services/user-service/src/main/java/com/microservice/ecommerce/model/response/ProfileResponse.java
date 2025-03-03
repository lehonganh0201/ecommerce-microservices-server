package com.microservice.ecommerce.model.response;

import java.time.LocalDateTime;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 9:19 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record ProfileResponse(
        Integer id,
        String avatar,
        boolean gender,
        LocalDateTime dateOfBirth
) {
}
