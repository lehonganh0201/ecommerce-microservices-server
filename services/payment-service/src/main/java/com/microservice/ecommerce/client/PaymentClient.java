package com.microservice.ecommerce.client;

import com.microservice.ecommerce.constant.Endpoint;
import com.microservice.ecommerce.model.global.GlobalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    24/03/2025 at 5:29 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@FeignClient(name = "order-service", url = "${application.config.order-service-url}")
public interface PaymentClient {
    @GetMapping(Endpoint.Order.ORDER_ID)
    ResponseEntity<GlobalResponse<OrderResponse>> findOrderById(@PathVariable(name = "orderId") UUID orderId);
}
