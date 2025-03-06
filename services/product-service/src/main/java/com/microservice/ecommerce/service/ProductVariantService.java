package com.microservice.ecommerce.service;

import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.request.ProductVariantRequest;
import com.microservice.ecommerce.model.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    06/03/2025 at 9:16 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface ProductVariantService {
    GlobalResponse<ProductResponse> createVariantToProduct(ProductVariantRequest variantRequest);

    GlobalResponse<ProductResponse> updateVariantProduct(UUID variantId, ProductVariantRequest variantRequest);

    GlobalResponse<String> deleteProductVariantById(UUID variantId);

    GlobalResponse<String> uploadImageToVariant(UUID variantId, MultipartFile image);
}
