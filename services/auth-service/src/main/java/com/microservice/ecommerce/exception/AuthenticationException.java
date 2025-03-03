package com.microservice.ecommerce.exception;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 7:34 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String s) {
        super(s);
    }
}
