package com.microservice.ecommerce.constant;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    24/03/2025 at 1:41 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface Endpoint {
    String PREFIX = "/api/v1";
    public interface Review {
        String PREFIX = Endpoint.PREFIX + "/reviews" ;
        String PRODUCT_ID = "/{productId}";
        String AVERAGE_RATING = "/{productId}/avg";
    }
}
