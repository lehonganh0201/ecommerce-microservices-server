package com.microservice.ecommerce.controller;

import com.microservice.ecommerce.constant.Endpoint;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.request.OrderItemRequest;
import com.microservice.ecommerce.model.request.ProductVariantRequest;
import com.microservice.ecommerce.model.request.PurchaseRequest;
import com.microservice.ecommerce.model.response.ProductPriceResponse;
import com.microservice.ecommerce.model.response.ProductResponse;
import com.microservice.ecommerce.service.ProductVariantService;
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
 * Created on:    06/03/2025 at 9:09 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoint.ProductVariant.PREFIX)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductVariantController {
    ProductVariantService variantService;

    @PostMapping()
    public ResponseEntity<GlobalResponse<ProductResponse>> createProductVariantToProduct(
            @RequestBody @Valid ProductVariantRequest variantRequest
    ) {
        return ResponseEntity
                .ok(variantService.createVariantToProduct(variantRequest));
    }

    @PutMapping(Endpoint.ProductVariant.VARIANT_ID)
    public ResponseEntity<GlobalResponse<ProductResponse>> updateProductVariant(
            @PathVariable(name = "variantId") UUID variantId,
            @RequestBody ProductVariantRequest variantRequest
    ) {
        return ResponseEntity
                .ok(variantService.updateVariantProduct(variantId, variantRequest));
    }

    @DeleteMapping(Endpoint.ProductVariant.VARIANT_ID)
    public ResponseEntity<GlobalResponse<String>> deleteProductVariantById(
            @PathVariable(name = "variantId") UUID variantId
    ) {
        return ResponseEntity
                .ok(variantService.deleteProductVariantById(variantId));
    }

    @PostMapping(Endpoint.ProductVariant.UPLOAD)
    public ResponseEntity<GlobalResponse<String>> uploadImageToVariant(
            @PathVariable(name = "variantId") UUID variantId,
            @RequestParam(name = "image")MultipartFile image
    ) {
        return ResponseEntity
                .ok(variantService.uploadImageToVariant(variantId, image));
    }

    @PostMapping(Endpoint.ProductVariant.CHECK_STOCK)
    public ResponseEntity<Boolean> checkStock(
            @RequestBody @Valid List<OrderItemRequest> requests
    ) {
        return ResponseEntity
                .ok(variantService.checkStock(requests));
    }

    @PostMapping(Endpoint.ProductVariant.GET_PRICE)
    public ResponseEntity<List<ProductPriceResponse>> getPrices(
            @RequestBody PurchaseRequest request
            ) {
        return ResponseEntity
                .ok(variantService.getPrices(request));
    }

    @PutMapping(Endpoint.ProductVariant.UPDATE_STOCK)
    public ResponseEntity<Void> updateStock(
            @RequestBody @Valid List<OrderItemRequest> requests
    ) {
        return ResponseEntity
                .ok(variantService.updateStock(requests));
    }
}
