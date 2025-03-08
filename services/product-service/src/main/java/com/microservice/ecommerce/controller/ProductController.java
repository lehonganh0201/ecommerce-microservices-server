package com.microservice.ecommerce.controller;

import com.microservice.ecommerce.constant.Endpoint;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.PageResponse;
import com.microservice.ecommerce.model.request.ProductRequest;
import com.microservice.ecommerce.model.response.ProductResponse;
import com.microservice.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;


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
    ProductService productService;

    @PostMapping()
    public ResponseEntity<GlobalResponse<ProductResponse>> createProduct(
            @ModelAttribute @Valid ProductRequest request
    ) {
        return ResponseEntity
                .ok(productService.createProduct(request));
    }

    @GetMapping()
    public ResponseEntity<GlobalResponse<PageResponse<ProductResponse>>> findAllProducts(
            @RequestParam(name = "sortedBy", required = false) String sortedBy,
            @RequestParam(name = "sortDirection", required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "searchKeyword", required = false) String searchKeyword,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "minPrice", required = false) Double minPrice,
            @RequestParam(name = "maxPrice", required = false) Double maxPrice,
            @RequestParam(name = "status", required = false, defaultValue = "true") boolean status
    ) {
        return ResponseEntity
                .ok(productService.findAllProducts(sortedBy, sortDirection, page, size, searchKeyword, category, minPrice, maxPrice, status));
    }

    @GetMapping(Endpoint.Product.PRODUCT_ID)
    public ResponseEntity<GlobalResponse<ProductResponse>> getProductById(
            @PathVariable(name = "productId")UUID productId
    ) {
        return ResponseEntity
                .ok(productService.getProductById(productId));
    }

    @GetMapping(Endpoint.Product.SEARCH)
    public ResponseEntity<GlobalResponse<List<ProductResponse>>> findByKeyword(
            @RequestParam(name = "keyword") String keyword
    ) {
        return ResponseEntity
                .ok(productService.searchByKeyword(keyword));
    }

    @PutMapping(Endpoint.Product.PRODUCT_ID)
    public ResponseEntity<GlobalResponse<ProductResponse>> updateProduct(
            @PathVariable(name = "productId") UUID productId,
            @ModelAttribute ProductRequest request
    ) {
        return ResponseEntity
                .ok(productService.updateProduct(productId, request));
    }

    @PutMapping(Endpoint.Product.UPLOAD)
    public ResponseEntity<GlobalResponse<ProductResponse>> uploadImage(
            @PathVariable(name = "productId") UUID productId,
            @RequestParam("images") List<MultipartFile> images
    ) {
        return ResponseEntity.ok(productService.uploadImage(productId, images));
    }

}
