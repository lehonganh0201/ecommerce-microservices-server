package com.microservice.ecommerce.message;

import java.util.List;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    15/03/2025 at 4:57 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record OrderConfirmation(
        String reference,
        Double totalAmount,
        String paymentMethod,
        String fullName,
        String email,
        List<ProductPriceResponse> products
) {
}
