package com.microservice.ecommerce.service;

import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.PageResponse;
import com.microservice.ecommerce.model.request.ProductRequest;
import com.microservice.ecommerce.model.response.ProductResponse;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    05/03/2025 at 7:56 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface ProductService {
    GlobalResponse<ProductResponse> createProduct(ProductRequest request);

    GlobalResponse<PageResponse<ProductResponse>> findAllProducts(String sortedBy, String sortDirection, int page, int size, String searchKeyword, String category, Double minPrice, Double maxPrice);
}
