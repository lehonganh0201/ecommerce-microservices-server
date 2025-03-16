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
 * Created on:    16/03/2025 at 2:42 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@FeignClient(name = "variant-service", url = "${application.config.variant-service-url}")
public interface ProductClient {

    @GetMapping(Endpoint.Variant.VARIANT_ID)
    ResponseEntity<GlobalResponse<ProductResponse>> findProductVariantById(@PathVariable(name = "variantId")UUID variantId);
}
