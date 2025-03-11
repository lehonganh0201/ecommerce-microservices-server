package com.microservice.ecommerce.exception;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    11/03/2025 at 1:19 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public class MoMoException extends Exception {
    public MoMoException(String message) {
        super(message);
    }

    public MoMoException(Throwable cause) {
        super(cause);
    }

}