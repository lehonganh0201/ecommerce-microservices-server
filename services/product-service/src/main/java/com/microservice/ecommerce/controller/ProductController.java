package com.microservice.ecommerce.controller;

import com.microservice.ecommerce.constant.Endpoint;
import com.microservice.ecommerce.model.global.GlobalResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    05/03/2025 at 7:29 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoint.Product.PREFIX)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
//    ProductService productService;
//
//    @PostMapping()
//    public ResponseEntity<GlobalResponse<ProductResponse>> createProduct(
//            @RequestBody @Valid ProductRequest request,
//
//    )
}
