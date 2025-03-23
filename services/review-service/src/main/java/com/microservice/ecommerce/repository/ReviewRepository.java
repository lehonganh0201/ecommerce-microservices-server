package com.microservice.ecommerce.repository;

import com.microservice.ecommerce.model.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    24/03/2025 at 1:04 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

    @Query("SELECT AVG(r.rating), COUNT(r) FROM Review r WHERE r.productId = :productId")
    Object[] findAverageRatingAndCount(@Param("productId") UUID productId);

    Page<Review> findByProductIdAndRatingBetween(UUID productId, int minRating, int maxRating, Pageable pageable);
}
