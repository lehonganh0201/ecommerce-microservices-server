package com.microservice.ecommerce.service;

import com.microservice.ecommerce.model.dto.request.ReviewRequest;
import com.microservice.ecommerce.model.dto.response.AverageRatingResponse;
import com.microservice.ecommerce.model.dto.response.ReviewResponse;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.PageResponse;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    24/03/2025 at 1:06 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface ReviewService {
    GlobalResponse<ReviewResponse> addReview(ReviewRequest request, Jwt jwt);
    GlobalResponse<PageResponse<ReviewResponse>> getReviewByProduct(UUID productId, int size, int page, String sortedBy, String sortDirection, String groupBy, int minRating, int maxRating);
    GlobalResponse<AverageRatingResponse> getAverageRating(UUID productId);
}
