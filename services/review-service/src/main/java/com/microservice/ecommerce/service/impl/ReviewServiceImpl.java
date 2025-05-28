package com.microservice.ecommerce.service.impl;

import com.microservice.ecommerce.model.dto.request.ReviewRequest;
import com.microservice.ecommerce.model.dto.response.AverageRatingResponse;
import com.microservice.ecommerce.model.dto.response.RatingResult;
import com.microservice.ecommerce.model.dto.response.ReviewResponse;
import com.microservice.ecommerce.model.entity.Review;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.PageResponse;
import com.microservice.ecommerce.model.global.Status;
import com.microservice.ecommerce.repository.ReviewRepository;
import com.microservice.ecommerce.service.ReviewService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    24/03/2025 at 1:07 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Service
@RequiredArgsConstructor
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewServiceImpl implements ReviewService {
    ReviewRepository reviewRepository;

    @Override
    public GlobalResponse<ReviewResponse> addReview(ReviewRequest request, Jwt jwt) {
        String userId = jwt.getSubject();
        String fullName = (String) jwt.getClaims().get("name");
        Review review = Review.builder()
                .userId(UUID.fromString(userId))
                .fullName(fullName)
                .productId(request.productId())
                .rating(request.rating())
                .comment(request.comment())
                .build();

        review = reviewRepository.save(review);

        return new GlobalResponse<>(
                Status.SUCCESS,
                getReviewResponse(review)
        );
    }

    private ReviewResponse getReviewResponse(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getProductId(),
                review.getFullName(),
                review.getRating(),
                review.getComment(),
                review.getCreatedDate()
        );
    }

    @Override
    public GlobalResponse<PageResponse<ReviewResponse>> getReviewByProduct(UUID productId, int size, int page, String sortedBy, String sortDirection, String groupBy, int minRating, int maxRating) {

        if (sortedBy == null || sortedBy.isBlank()) {
            sortedBy = "rating";
        }

        Sort sort = sortDirection != null && sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortedBy).ascending()
                : Sort.by(sortedBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Review> reviewPage = reviewRepository.findByProductIdAndRatingBetween(
                productId, minRating, maxRating, pageable);

        List<ReviewResponse> reviewResponses = reviewPage.getContent()
                .stream()
                .map(review -> getReviewResponse(review))
                .collect(Collectors.toList());

        return new GlobalResponse<>(
                Status.SUCCESS,
                new PageResponse<>(
                        reviewResponses,
                        reviewPage.getTotalPages(),
                        reviewPage.getTotalElements(),
                        reviewPage.getNumber(),
                        reviewPage.getSize(),
                        reviewPage.getNumberOfElements(),
                        reviewPage.isFirst(),
                        reviewPage.isLast(),
                        reviewPage.hasNext(),
                        reviewPage.hasPrevious()
                )
        );
    }

    @Override
    public GlobalResponse<AverageRatingResponse> getAverageRating(UUID productId) {
        RatingResult result = reviewRepository.findAverageRatingAndCount(productId);

        Double averageRating = result.averageRating() != null ? result.averageRating() : 0;
        Long reviewCount = result.reviewCount();

        return new GlobalResponse<>(
                Status.SUCCESS,
                new AverageRatingResponse(
                        averageRating,
                        reviewCount
                )
        );
    }
}
