package com.microservice.ecommerce.exception;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 10:38 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public class BusinessException extends RuntimeException {
    public BusinessException(String s) {
        super(s);
    }
}
