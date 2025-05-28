package com.microservice.ecommerce.model.dto.response;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    29/05/2025 at 12:36 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record RatingResult(
        Double averageRating,
        Long reviewCount
) {
}
