package com.microservice.ecommerce.exception;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    16/03/2025 at 5:52 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public class BussinessException extends RuntimeException {
    public BussinessException(String s) {
        super(s);
    }
}
