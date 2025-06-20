package com.microservice.ecommerce.service;

import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.request.ProductAttributeRequest;
import com.microservice.ecommerce.model.response.ProductAttributeResponse;

import java.util.List;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    08/06/2025 at 6:53 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface ProductAttributeService {
    GlobalResponse<ProductAttributeResponse> createAttribute(UUID variantId, ProductAttributeRequest request);
    GlobalResponse<ProductAttributeResponse> getAttributeById(UUID id);
    GlobalResponse<List<ProductAttributeResponse>> getAttributesByVariantId(UUID variantId);
    GlobalResponse<ProductAttributeResponse> updateAttribute(UUID id, ProductAttributeRequest request);
    GlobalResponse<Boolean> deleteAttribute(UUID id);
    GlobalResponse<List<ProductAttributeResponse>> findByTypeAndValue(String type, String value);
}
