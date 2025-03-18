package com.microservice.ecommerce.handle;

import com.microservice.ecommerce.exception.BussinessException;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    18/03/2025 at 11:30 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(BussinessException.class)
    public ResponseEntity<GlobalResponse<String>> handleException(BussinessException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new GlobalResponse<>(
                        Status.ERROR,
                        ex.getMessage()
                ));
    }
}
