package com.microservice.ecommerce.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    16/03/2025 at 2:28 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductAttribute(
        String type,
        String value
) {
}
