package com.microservice.ecommerce.exception;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    10/03/2025 at 8:43 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public class BusinessException extends RuntimeException {
    public BusinessException(String s) {
        super(s);
    }
}
