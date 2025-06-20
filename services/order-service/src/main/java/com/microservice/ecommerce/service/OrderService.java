package com.microservice.ecommerce.service;

import com.microservice.ecommerce.model.dto.request.OrderRequest;
import com.microservice.ecommerce.model.dto.response.OrderResponse;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.PageResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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

    GlobalResponse<PageResponse<OrderResponse>> findOwnOrders(int page, int size, String type, Jwt jwt);

    GlobalResponse<OrderResponse> findOrderById(UUID orderId);

    GlobalResponse<OrderResponse> changeOrderStatus(UUID orderId, String orderStatus);

    GlobalResponse<OrderResponse> getByReference(String reference, Jwt jwt);

    GlobalResponse<PageResponse<OrderResponse>> findAllOrders(int page, int size, String sortedBy, String sortDirection, String status, String customerId, String paymentMethod, Double minTotal, Double maxTotal, String productId, String deliveryMethod, LocalDateTime startDate, LocalDateTime endDate);

    GlobalResponse<String> confirmationOrder(Map<String, String> requestParams);
}
