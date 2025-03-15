package com.microservice.ecommerce.message;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    15/03/2025 at 11:51 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record PaymentConfirmation(
        String email,
        String name,
        double amount,
        String orderReference
) {
}