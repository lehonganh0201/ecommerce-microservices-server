package com.microservice.ecommerce.constant;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    23/03/2025 at 10:32 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface Endpoint {
    String PREFIX = "/api/v1";

    public interface Analytics {
        String PREFIX = Endpoint.PREFIX + "/analytics";
        String REVENUE = "/revenue";
    }

    public interface Order {
        String FIND_ALL = "/all";
    }
}
