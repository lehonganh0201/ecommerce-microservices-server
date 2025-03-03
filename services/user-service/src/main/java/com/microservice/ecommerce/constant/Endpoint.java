package com.microservice.ecommerce.constant;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 7:42 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface Endpoint {
    String PREFIX = "/api/v1";

    public interface User {
        String PREFIX = Endpoint.PREFIX + "/users";
        String ME = "/me";
    }

    public interface Profile {
        String PREFIX = Endpoint.PREFIX + "/profiles";
    }
}
