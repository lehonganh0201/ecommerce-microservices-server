package com.microservice.ecommerce.model.mapper;

import com.microservice.ecommerce.model.entity.Product;
import com.microservice.ecommerce.model.request.ProductRequest;
import com.microservice.ecommerce.model.response.ProductResponse;
import org.mapstruct.*;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    05/03/2025 at 10:16 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Mapper(componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProductMapper {
    Product toProduct(ProductRequest request);

    @Mapping(target = "images", ignore = true)
    @Mapping(target = "variants", ignore = true)
    ProductResponse toProductResponse(Product product);

    @Mapping(target = "images", ignore = true)
    void updateProduct(ProductRequest request, @MappingTarget Product product);
}