package com.microservice.ecommerce.client;

import com.microservice.ecommerce.constant.Endpoint;
import com.microservice.ecommerce.model.dto.request.OrderItemRequest;
import com.microservice.ecommerce.model.dto.response.ProductPriceResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    09/03/2025 at 5:55 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@FeignClient(name = "variant-service", url = "${application.config.variant-service-url}")
public interface ProductClient {

    @PostMapping(Endpoint.Variant.CHECK_STOCK)
    ResponseEntity<Boolean> checkStock(@RequestBody @Valid List<OrderItemRequest> items);

    @PutMapping(Endpoint.Variant.UPDATE_STOCK)
    ResponseEntity<Void> updateStock(@RequestBody @Valid List<OrderItemRequest> items);

    @PostMapping(Endpoint.Variant.GET_PRICE)
    ResponseEntity<List<ProductPriceResponse>> getProductPrices(@RequestBody List<UUID> variantIds);
}
