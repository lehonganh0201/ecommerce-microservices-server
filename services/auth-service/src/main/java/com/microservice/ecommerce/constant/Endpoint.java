package com.microservice.ecommerce.constant;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 7:04 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface Endpoint {
    String PREFIX = "/api/v1";
    public interface Auth {
        String PREFIX = Endpoint.PREFIX + "/auths";
        String REGISTER = "/register";
        String LOGIN = "/login";
        String FORGOT_PASSWORD = "/forgot-password";
    }
}
