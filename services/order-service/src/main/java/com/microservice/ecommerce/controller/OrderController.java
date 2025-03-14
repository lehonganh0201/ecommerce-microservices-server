package com.microservice.ecommerce.controller;

import com.microservice.ecommerce.constant.Endpoint;
import com.microservice.ecommerce.model.dto.request.OrderRequest;
import com.microservice.ecommerce.model.dto.response.OrderResponse;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    09/03/2025 at 5:29 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoint.Order.PREFIX)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;

    @PostMapping()
    public ResponseEntity<GlobalResponse<OrderResponse>> createOrder(
            @RequestBody @Valid OrderRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity
                .ok(orderService.createOrder(request, jwt));
    }

    @GetMapping()
    public ResponseEntity<GlobalResponse<List<OrderResponse>>> getOwnOrders(
            @RequestParam(name = "orderType", required = false) String type,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity
                .ok(orderService.findOwnOrders(type, jwt));
    }

    @GetMapping(Endpoint.Order.ORDER_ID)
    public ResponseEntity<GlobalResponse<OrderResponse>> getOrderById(
            @PathVariable(name = "orderId")UUID orderId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity
                .ok(orderService.findOrderById(orderId, jwt));
    }

    @PutMapping(Endpoint.Order.ORDER_ID)
    public ResponseEntity<GlobalResponse<OrderResponse>> canceledOrder(
            @PathVariable(name = "orderId") UUID orderId,
            @RequestParam(name = "status") String orderStatus,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity
                .ok(orderService.canceledOrderById(orderId, orderStatus, jwt));
    }
}
