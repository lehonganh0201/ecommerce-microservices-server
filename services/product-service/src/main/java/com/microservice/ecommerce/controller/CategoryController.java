package com.microservice.ecommerce.controller;

import com.microservice.ecommerce.constant.Endpoint;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.PageResponse;
import com.microservice.ecommerce.model.request.CategoryRequest;
import com.microservice.ecommerce.model.response.CategoryResponse;
import com.microservice.ecommerce.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Tạo danh mục mới", description = "Tạo một danh mục mới dựa trên thông tin gửi lên.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tạo danh mục thành công",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ", content = @Content)
    })
    @PostMapping()
    public ResponseEntity<GlobalResponse<CategoryResponse>> createCategory(
            @RequestBody @Valid CategoryRequest request
    ) {
        return ResponseEntity.ok(categoryService.createCategory(request));
    }

    @Operation(summary = "Lấy tất cả danh mục", description = "Lấy danh sách các danh mục với phân trang và sắp xếp.")
    @ApiResponse(responseCode = "200", description = "Lấy danh mục thành công",
            content = @Content(schema = @Schema(implementation = PageResponse.class)))
    @GetMapping()
    public ResponseEntity<GlobalResponse<PageResponse<CategoryResponse>>> findAllCategories(
            @RequestParam(name = "sortedBy", required = false) String sortedBy,
            @RequestParam(name = "sortDirection", required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "searchKeyword", required = false) String searchKeyword
    ) {
        return ResponseEntity.ok(categoryService.findAllCategories(sortedBy, sortDirection, page, size, searchKeyword));
    }

    @Operation(summary = "Cập nhật danh mục", description = "Cập nhật thông tin danh mục theo UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật danh mục thành công",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy danh mục", content = @Content)
    })
    @PutMapping(Endpoint.Category.CATEGORY_ID)
    public ResponseEntity<GlobalResponse<CategoryResponse>> updateCategory(
            @PathVariable(name = "categoryId") UUID categoryId,
            @RequestBody @Valid CategoryRequest request
    ) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryId, request));
    }

    @Operation(summary = "Xóa danh mục", description = "Xóa danh mục dựa trên UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Xóa danh mục thành công",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy danh mục", content = @Content)
    })
    @DeleteMapping(Endpoint.Category.CATEGORY_ID)
    public ResponseEntity<GlobalResponse<String>> deleteCategory(
            @PathVariable(name = "categoryId") UUID categoryId
    ) {
        return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
    }
}
