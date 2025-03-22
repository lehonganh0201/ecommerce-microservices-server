package com.microservice.ecommerce.service;

import com.microservice.ecommerce.model.dto.request.OrderRequest;
import com.microservice.ecommerce.model.dto.response.OrderResponse;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.PageResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    09/03/2025 at 5:40 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface OrderService {
    GlobalResponse<OrderResponse> createOrder(OrderRequest request, Jwt jwt);

    GlobalResponse<List<OrderResponse>> findOwnOrders(String type, Jwt jwt);

    GlobalResponse<OrderResponse> findOrderById(UUID orderId, Jwt jwt);

    GlobalResponse<OrderResponse> canceledOrderById(UUID orderId, String orderStatus, Jwt jwt);

    GlobalResponse<OrderResponse> getByReference(String reference, Jwt jwt);

    GlobalResponse<PageResponse<OrderResponse>> findAllOrders(int page, int size, String sortedBy, String sortDirection, String status, String customerId, String paymentMethod, Double minTotal, Double maxTotal, String productId, String deliveryMethod, LocalDateTime startDate, LocalDateTime endDate);
}
