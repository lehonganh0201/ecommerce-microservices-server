package com.microservice.ecommerce.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 8:01 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record UserRequest(
        @NotBlank(message = "Phone number cannot be blank")
        String phoneNumber,

        @Valid
        AddressRequest address
) {
}