package com.microservice.ecommerce.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    05/03/2025 at 7:55 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Schema(description = "Yêu cầu thông tin sản phẩm")
public record ProductRequest(
        @Schema(description = "Tên sản phẩm", example = "Giày thể thao Nike Air Max")
        @NotBlank(message = "Tên sản phẩm không được để trống")
        String name,

        @Schema(description = "Mô tả sản phẩm", example = "Giày thể thao cao cấp với công nghệ đệm khí Air Max")
        String description,

        @Schema(description = "Giá sản phẩm", example = "1500000")
        @NotNull(message = "Giá sản phẩm không được null")
        @Min(value = 0, message = "Giá sản phẩm phải lớn hơn hoặc bằng 0")
        Double price,

        @Schema(description = "Số lượng tồn kho", example = "100")
        @NotNull(message = "Số lượng tồn kho không được null")
        @Min(value = 0, message = "Số lượng tồn kho phải lớn hơn hoặc bằng 0")
        Integer stock,

        @Schema(description = "Mã danh mục sản phẩm", example = "123e4567-e89b-12d3-a456-426614174000")
        @NotNull(message = "Danh mục sản phẩm không được null")
        UUID categoryId,

        @Schema(description = "Danh sách hình ảnh của sản phẩm")
        List<MultipartFile> images
) {
}