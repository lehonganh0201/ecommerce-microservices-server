package com.microservice.ecommerce.service;

import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.PageResponse;
import com.microservice.ecommerce.model.request.CategoryRequest;
import com.microservice.ecommerce.model.response.CategoryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    06/03/2025 at 6:04 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface CategoryService {
    GlobalResponse<CategoryResponse> createCategory(CategoryRequest request);

    GlobalResponse<PageResponse<CategoryResponse>> findAllCategories(String sortedBy, String sortDirection, int page, int size, String searchKeyword);

    GlobalResponse<CategoryResponse> updateCategory(UUID categoryId, CategoryRequest request);

    GlobalResponse<String> deleteCategory(UUID categoryId);

    GlobalResponse<CategoryResponse> uploadImage(UUID categoryId, MultipartFile image);

    GlobalResponse<CategoryResponse> findCategoryById(UUID categoryId);
}
