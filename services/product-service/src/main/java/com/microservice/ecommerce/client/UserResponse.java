package com.microservice.ecommerce.client;

import java.util.List;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 8:01 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record UserResponse(
        String username,
        String fullName,
        String email,
        String phoneNumber,
        List<AddressResponse> addresses
) {
}
