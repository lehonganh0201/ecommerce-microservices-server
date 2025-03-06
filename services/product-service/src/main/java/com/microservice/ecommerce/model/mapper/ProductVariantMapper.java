package com.microservice.ecommerce.model.mapper;

import com.microservice.ecommerce.model.entity.ProductVariant;
import com.microservice.ecommerce.model.request.ProductVariantRequest;
import com.microservice.ecommerce.model.response.ProductVariantResponse;
import org.mapstruct.*;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    06/03/2025 at 12:42 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProductVariantMapper {

    @Mapping(target = "attributes", ignore = true)
    ProductVariant toProductVariant(ProductVariantRequest request);

    @Mapping(target = "attributes", ignore = true)
    ProductVariantResponse toProductVariantResponse(ProductVariant productVariant);

    void updateProductVariant(ProductVariantRequest request, @MappingTarget ProductVariant variant);
}
