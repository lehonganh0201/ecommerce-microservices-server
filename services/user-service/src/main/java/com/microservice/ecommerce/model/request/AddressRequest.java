package com.microservice.ecommerce.model.request;

import jakarta.validation.constraints.NotBlank;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 8:19 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record AddressRequest (
        @NotBlank(message = "Street cannot be blank")
        String street,

        @NotBlank(message = "City cannot be blank")
        String city,

        @NotBlank(message = "State cannot be blank")
        String state,

        @NotBlank(message = "Country cannot be blank")
        String country,

        String zipCode,

        String description
) {
}
