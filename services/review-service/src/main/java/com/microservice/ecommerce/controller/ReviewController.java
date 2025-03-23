package com.microservice.ecommerce.controller;

import com.microservice.ecommerce.constant.Endpoint;
import com.microservice.ecommerce.model.dto.request.ReviewRequest;
import com.microservice.ecommerce.model.dto.response.AverageRatingResponse;
import com.microservice.ecommerce.model.dto.response.ReviewResponse;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.PageResponse;
import com.microservice.ecommerce.service.ReviewService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    24/03/2025 at 12:57 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(Endpoint.Review.PREFIX)
public class ReviewController {
    ReviewService reviewService;

    @PostMapping
    public ResponseEntity<GlobalResponse<ReviewResponse>> addReview(
            @RequestBody @Valid ReviewRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity
                .ok(reviewService.addReview(request, jwt));
    }

    @GetMapping(Endpoint.Review.PRODUCT_ID)
    public ResponseEntity<GlobalResponse<PageResponse<ReviewResponse>>> getReviewByProduct(
            @PathVariable(name = "productId") UUID productId,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "20") int size,
            @RequestParam(name = "sortedBy", required = false) String sortedBy,
            @RequestParam(name = "sortDirection", required = false, defaultValue = "desc") String sortDirection,
            @RequestParam(name = "minRating", required = false) int minRating,
            @RequestParam(name = "maxRating", required = false) int maxRating
    ) {
        return ResponseEntity
                .ok(reviewService.getReviewByProduct(productId, page, size, sortedBy, sortDirection,null, minRating, maxRating));
    }

    @GetMapping(Endpoint.Review.AVERAGE_RATING)
    public ResponseEntity<GlobalResponse<AverageRatingResponse>> getAverageRatingFromProduct(
            @PathVariable(name = "productId") UUID productId
    ) {
        return ResponseEntity
                .ok(reviewService.getAverageRating(productId));
    }
}
