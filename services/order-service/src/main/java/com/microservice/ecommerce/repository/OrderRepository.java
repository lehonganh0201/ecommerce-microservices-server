package com.microservice.ecommerce.repository;

import com.microservice.ecommerce.constant.OrderStatus;
import com.microservice.ecommerce.constant.PaymentMethod;
import com.microservice.ecommerce.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
    Page<Order> findAllByUserIdAndStatus(@Param("userId") String userId,
                                         @Param("status") String status,
                                         Pageable pageable);

    Optional<Order> findByReference(String reference);

    @Query("SELECT DISTINCT o FROM Order o " +
            "LEFT JOIN o.orderItems oi " +
            "WHERE " +
            "(COALESCE(:status, NULL) IS NULL OR o.status = :status) AND " +
            "(COALESCE(:customerId, NULL) IS NULL OR o.userId = :customerId) AND " +
            "(COALESCE(:paymentMethod, NULL) IS NULL OR o.paymentMethod = :paymentMethod) AND " +
            "(COALESCE(:minTotal, 0) = 0 OR o.totalAmount >= :minTotal) AND " +
            "(COALESCE(:maxTotal, 0) = 0 OR o.totalAmount <= :maxTotal) AND " +
            "(COALESCE(:productId, NULL) IS NULL OR oi.productId = :productId) AND " +
            "(COALESCE(:startDate, NULL) IS NULL OR o.createdDate >= :startDate) AND " +
            "(COALESCE(:endDate, NULL) IS NULL OR o.createdDate <= :endDate)")
    Page<Order> findAllOrders(
            @Param("status") OrderStatus status,
            @Param("customerId") String customerId,
            @Param("paymentMethod") PaymentMethod paymentMethod,
            @Param("minTotal") Double minTotal,
            @Param("maxTotal") Double maxTotal,
            @Param("productId") String productId,
            @Param("deliveryMethod") String deliveryMethod,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );
}
