package com.microservice.ecommerce.client;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 8:47 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record AddressResponse(
        Integer id,
        String street,
        String city,
        String state,
        String country,
        String zipCode,
        String description
) {
}
