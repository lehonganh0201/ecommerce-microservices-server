package com.microservice.ecommerce.constant;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    16/03/2025 at 2:44 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface Endpoint {
    String PREFIX = "/api/v1";

    public interface Variant {
        String VARIANT_ID = "/{variantId}";
    }

    public interface Cart {
        String PREFIX = Endpoint.PREFIX + "/carts";
    }
}
