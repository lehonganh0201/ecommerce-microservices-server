package com.microservice.ecommerce.message;

import com.microservice.ecommerce.model.dto.response.ProductPriceResponse;

import java.util.List;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    15/03/2025 at 3:57 PM
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
