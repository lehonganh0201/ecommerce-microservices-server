package com.microservice.ecommerce.model.mapper;

import com.microservice.ecommerce.model.entity.Category;
import com.microservice.ecommerce.model.request.CategoryRequest;
import com.microservice.ecommerce.model.response.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    06/03/2025 at 6:06 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CategoryMapper {
   Category toCategory(CategoryRequest request);

   CategoryResponse toCategoryResponse(Category category);

   void updateCategory(CategoryRequest request, @MappingTarget Category category);
}
