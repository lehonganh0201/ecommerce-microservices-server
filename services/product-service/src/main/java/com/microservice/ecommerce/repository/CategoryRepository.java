package com.microservice.ecommerce.repository;

import com.microservice.ecommerce.model.entity.Category;
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
 * Created on:    05/03/2025 at 10:56 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    @Query("SELECT c FROM Category c WHERE " +
            "(COALESCE(:searchKeyword, '') = '' OR LOWER(c.name) LIKE LOWER(CONCAT('%', :searchKeyword, '%')))")
    Page<Category> findAllWithFilters(
            @Param("searchKeyword") String searchKeyword,
            Pageable pageable
    );

    boolean existsById(UUID id);
}
