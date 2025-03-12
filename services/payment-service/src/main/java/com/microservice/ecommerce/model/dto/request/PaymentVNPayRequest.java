package com.microservice.ecommerce.model.dto.request;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    12/03/2025 at 9:07 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record PaymentVNPayRequest(
        int amount,
        String bankCode,
        String language
) {
}
