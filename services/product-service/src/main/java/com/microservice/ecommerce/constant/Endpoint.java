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
        String SEARCH = "/search";
        String STATUS = PRODUCT_ID + "/status";
    }

    public interface Category {
        String PREFIX = Endpoint.PREFIX + "/categories";
        String CATEGORY_ID = "/{categoryId}";
        String UPLOAD = CATEGORY_ID + "/upload";
    }

    public interface ProductVariant {
        String PREFIX = Endpoint.PREFIX + "/variants";
        String VARIANT_ID = "/{variantId}";
        String UPLOAD = VARIANT_ID + "/upload";
        String CHECK_STOCK = "/check-stock";
        String UPDATE_STOCK = "/update-stock";
        String GET_PRICE = "/get-prices";
    }

    public interface ProductAttribute {
        String PREFIX = Endpoint.PREFIX + "/attributes";
        String ATTRIBUTE_ID = "/{id}";
        String VARIANT = "/variant/{variantId}";
        String SEARCH = "/search";
    }
}
