package com.microservice.ecommerce.service.impl;

import com.microservice.ecommerce.model.entity.ProductAttribute;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.Status;
import com.microservice.ecommerce.model.request.ProductAttributeRequest;
import com.microservice.ecommerce.model.response.ProductAttributeResponse;
import com.microservice.ecommerce.repository.ProductAttributeRepository;
import com.microservice.ecommerce.repository.ProductVariantRepository;
import com.microservice.ecommerce.service.ProductAttributeService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    08/06/2025 at 6:54 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Service
@RequiredArgsConstructor
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductAttributeServiceImpl implements ProductAttributeService {
    ProductAttributeRepository productAttributeRepository;
    ProductVariantRepository productVariantRepository;

    @Override
    @Transactional
    public GlobalResponse<ProductAttributeResponse> createAttribute(UUID variantId, ProductAttributeRequest request) {
        ProductAttributeResponse response = productVariantRepository.findById(variantId)
                .map(variant -> ProductAttribute.builder()
                        .type(request.type())
                        .value(request.value())
                        .productVariant(variant)
                        .build())
                .map(productAttributeRepository::save)
                .map(attribute -> new ProductAttributeResponse(attribute.getId(), attribute.getType().getValue(), attribute.getValue()))
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy biến thể sản phẩm với id: " + variantId));

        return new GlobalResponse<>(Status.SUCCESS, response);
    }

    @Override
    public GlobalResponse<ProductAttributeResponse> getAttributeById(UUID id) {
        ProductAttribute attribute =  productAttributeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy thuộc tính sản phẩm với id: " + id));

        ProductAttributeResponse response = ProductAttributeResponse.builder()
                .id(attribute.getId())
                .type(attribute.getType().getValue())
                .value(attribute.getValue())
                .build();

        return new GlobalResponse<>(Status.SUCCESS, response);
    }

    @Override
    public GlobalResponse<List<ProductAttributeResponse>> getAttributesByVariantId(UUID variantId) {
        List<ProductAttribute> attributes = productAttributeRepository.findByProductVariantId(variantId);

        List<ProductAttributeResponse> responses = attributes.stream().map(attribute ->
                new ProductAttributeResponse(attribute.getId(), attribute.getType().getValue(), attribute.getValue()))
                .toList();
        return new GlobalResponse<>(Status.SUCCESS, responses);
    }

    @Override
    @Transactional
    public GlobalResponse<ProductAttributeResponse> updateAttribute(UUID id, ProductAttributeRequest request) {
        ProductAttributeResponse response = productAttributeRepository.findById(id)
                .map(attribute -> {
                    attribute.setType(request.type());
                    attribute.setValue(request.value());
                    return productAttributeRepository.save(attribute);
                })
                .map(attribute -> new ProductAttributeResponse(attribute.getId(), attribute.getType().getValue(), attribute.getValue()))
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy thuộc tính sản phẩm với id: " + id));

        return new GlobalResponse<>(Status.SUCCESS, response);
    }

    @Override
    public GlobalResponse<Boolean> deleteAttribute(UUID id) {
        if (!productAttributeRepository.existsById(id)) {
            throw new IllegalArgumentException("Không tìm thấy thuộc tính sản phẩm với id: " + id);
        }
        productAttributeRepository.deleteById(id);

        return new GlobalResponse<>(Status.SUCCESS, Boolean.TRUE);
    }

    @Override
    public GlobalResponse<List<ProductAttributeResponse>> findByTypeAndValue(String type, String value) {
        List<ProductAttributeResponse> responses = productAttributeRepository.findByTypeAndValue(type, value).stream().map(attribute -> new ProductAttributeResponse(attribute.getId(), attribute.getType().getValue(), attribute.getValue())).toList();

        return new GlobalResponse<>(Status.SUCCESS, responses);
    }
}
