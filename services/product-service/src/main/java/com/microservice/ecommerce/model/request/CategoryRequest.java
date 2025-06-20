package com.microservice.ecommerce.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    09/03/2025 at 7:55 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Schema(description = "Yêu cầu thông tin danh mục sản phẩm")
public record CategoryRequest(
        @Schema(description = "Tên danh mục", example = "Giày thể thao")
        @NotBlank(message = "Category name cannot be blank")
        @Size(min = 3, max = 50, message = "Category name must be between 3 and 50 characters")
        String name,

        @Schema(description = "Mô tả danh mục", example = "Các loại giày thể thao đa dạng mẫu mã")
        @Size(max = 255, message = "Description cannot exceed 255 characters")
        String description,

        MultipartFile image
) {
}
