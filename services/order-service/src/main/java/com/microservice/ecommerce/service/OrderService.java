package com.microservice.ecommerce.service;

import com.microservice.ecommerce.model.dto.request.OrderRequest;
import com.microservice.ecommerce.model.dto.response.OrderResponse;
import com.microservice.ecommerce.model.global.GlobalResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.jwt.Jwt;

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
}
