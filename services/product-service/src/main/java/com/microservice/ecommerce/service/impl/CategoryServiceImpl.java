package com.microservice.ecommerce.service.impl;

import com.microservice.ecommerce.model.entity.Category;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.PageResponse;
import com.microservice.ecommerce.model.global.Status;
import com.microservice.ecommerce.model.mapper.CategoryMapper;
import com.microservice.ecommerce.model.request.CategoryRequest;
import com.microservice.ecommerce.model.response.CategoryResponse;
import com.microservice.ecommerce.repository.CategoryRepository;
import com.microservice.ecommerce.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    06/03/2025 at 6:05 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Service
@RequiredArgsConstructor
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;

    CategoryMapper categoryMapper;

    @Override
    public GlobalResponse<CategoryResponse> createCategory(CategoryRequest request) {
        Category category = categoryMapper.toCategory(request);

        category = categoryRepository.save(category);

        return new GlobalResponse<>(
                Status.SUCCESS,
                categoryMapper.toCategoryResponse(category)
        );
    }

    @Override
    public GlobalResponse<PageResponse<CategoryResponse>> findAllCategories(
            String sortedBy, String sortDirection,
            int page, int size,
            String searchKeyword) {
        Sort sort = Sort.by(sortDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,
                sortedBy != null ? sortedBy : "id" );

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Category> categoryPage = categoryRepository.findAllWithFilters(searchKeyword, pageable);

        List<CategoryResponse> responses = categoryPage.stream()
                .map(categoryMapper::toCategoryResponse)
                .collect(Collectors.toList());

        return new GlobalResponse<>(
                Status.SUCCESS,
                new PageResponse<>(
                        responses,
                        categoryPage.getTotalPages(),
                        categoryPage.getTotalElements(),
                        categoryPage.getNumber(),
                        categoryPage.getSize(),
                        categoryPage.getNumberOfElements(),
                        categoryPage.isFirst(),
                        categoryPage.isLast(),
                        categoryPage.hasNext(),
                        categoryPage.hasPrevious()
                )
        );
    }

    @Override
    public GlobalResponse<CategoryResponse> updateCategory(UUID categoryId, CategoryRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Not found category with the ID provided"));

        categoryMapper.updateCategory(request, category);

        category = categoryRepository.save(category);

        return new GlobalResponse<>(
                Status.SUCCESS,
                categoryMapper.toCategoryResponse(category)
        );
    }

    @Override
    public GlobalResponse<String> deleteCategory(UUID categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Danh mục không tồn tại");
        }
        categoryRepository.deleteById(categoryId);

        return new GlobalResponse<>(
          Status.SUCCESS,
          "Xóa danh mục thành công"
        );
    }
}
