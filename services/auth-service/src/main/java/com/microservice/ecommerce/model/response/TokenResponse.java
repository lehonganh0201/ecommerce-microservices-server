package com.microservice.ecommerce.model.response;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 6:53 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record TokenResponse(
        String accessToken,
        String refreshToken,
        Long expiresIn,
        String returnUrl
) {
}
