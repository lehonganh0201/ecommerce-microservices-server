package com.microservice.ecommerce.constant;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    05/03/2025 at 7:31 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface Endpoint {
    String PREFIX = "/api/v1";

    public interface Product {
        String PREFIX = Endpoint.PREFIX + "/products";
        String PRODUCT_ID = "/{productId}";
        String UPLOAD = PRODUCT_ID + "/upload";
    }
}
