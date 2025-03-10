package com.microservice.ecommerce.repository;

import com.microservice.ecommerce.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    09/03/2025 at 5:43 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("SELECT o FROM Order o WHERE o.userId = :userId " +
            "AND (o.status = COALESCE(NULLIF(:status, ''), o.status))")
    List<Order> findAllByUserIdAndStatus(@Param("userId") String userId,
                                         @Param("status") String status);

}
