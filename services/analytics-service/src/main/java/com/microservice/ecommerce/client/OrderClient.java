package com.microservice.ecommerce.client;

import com.microservice.ecommerce.constant.Endpoint;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/03/2025 at 9:22 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@FeignClient(name = "order-service", url = "${application.config.order-service-url}")
public interface OrderClient {

    @GetMapping(Endpoint.Order.FIND_ALL)
    GlobalResponse<PageResponse<OrderResponse>> findAllOrders(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "1000") int size,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    );
}
