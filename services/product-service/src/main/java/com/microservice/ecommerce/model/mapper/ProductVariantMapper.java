package com.microservice.ecommerce.model.mapper;

import com.microservice.ecommerce.model.entity.ProductVariant;
import com.microservice.ecommerce.model.response.ProductVariantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    06/03/2025 at 12:42 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProductVariantMapper {

    @Mapping(target = "attributes", ignore = true)
    ProductVariantResponse toProductVariantResponse(ProductVariant productVariant);
}
