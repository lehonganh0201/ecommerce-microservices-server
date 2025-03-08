package com.microservice.ecommerce.repository;

import com.microservice.ecommerce.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    05/03/2025 at 9:50 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("SELECT p FROM Product p JOIN p.category c WHERE " +
            "(COALESCE(:searchKeyword, '') = '' OR LOWER(p.name) LIKE LOWER(CONCAT('%', :searchKeyword, '%'))) AND " +
            "(COALESCE(:category, '') = '' OR LOWER(CAST(c.name AS string)) = LOWER(:category)) AND " +
            "(COALESCE(:minPrice, 0) = 0 OR p.price >= :minPrice) AND " +
            "(COALESCE(:maxPrice, 0) = 0 OR p.price <= :maxPrice) AND " +
            "p.isActive = :status")
    Page<Product> findAllWithFilters(
            @Param("searchKeyword") String keyword,
            @Param("category") String category,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("status") Boolean status,
            Pageable pageable
    );
}
