package com.microservice.ecommerce.controller;

import com.microservice.ecommerce.constant.Endpoint;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.PageResponse;
import com.microservice.ecommerce.model.request.CategoryRequest;
import com.microservice.ecommerce.model.response.CategoryResponse;
import com.microservice.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    06/03/2025 at 6:01 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoint.Category.PREFIX)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {
    CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<GlobalResponse<CategoryResponse>> createCategory(
            @RequestBody @Valid CategoryRequest request
    ) {
        return ResponseEntity
                .ok(categoryService.createCategory(request));
    }

    @GetMapping()
    public ResponseEntity<GlobalResponse<PageResponse<CategoryResponse>>> findAllCategories(
            @RequestParam(name = "sortedBy", required = false) String sortedBy,
            @RequestParam(name = "sortDirection", required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "searchKeyword", required = false) String searchKeyword
    ) {
        return ResponseEntity
                .ok(categoryService.findAllCategories(sortedBy, sortDirection, page, size, searchKeyword));
    }

    @PutMapping(Endpoint.Category.CATEGORY_ID)
    public ResponseEntity<GlobalResponse<CategoryResponse>> updateCategory(
            @PathVariable(name = "categoryId")UUID categoryId,
            @RequestBody CategoryRequest request
    ) {
        return ResponseEntity
                .ok(categoryService.updateCategory(categoryId, request));
    }

    @DeleteMapping(Endpoint.Category.CATEGORY_ID)
    public ResponseEntity<GlobalResponse<String>> deleteCategory(
            @PathVariable(name = "categoryId")UUID categoryId
    ) {
        return ResponseEntity
                .ok(categoryService.deleteCategory(categoryId));
    }
}
