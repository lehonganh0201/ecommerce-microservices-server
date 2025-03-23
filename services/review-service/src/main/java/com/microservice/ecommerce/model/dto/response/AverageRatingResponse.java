package com.microservice.ecommerce.model.dto.response;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    24/03/2025 at 1:14 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record AverageRatingResponse(
        Double averageRating,
        Long totalReviews
) {
}
