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
 * Created on:    05/03/2025 at 10:41 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Schema(description = "Yêu cầu biến thể sản phẩm")
public record ProductVariantRequest(
        @Schema(description = "Mã sản phẩm", example = "123e4567-e89b-12d3-a456-426614174000")
        @NotNull(message = "Mã sản phẩm không được null")
        UUID productId,

        @Schema(description = "Giá biến thể", example = "250.5")
        @NotNull(message = "Giá biến thể không được null")
        @Min(value = 0, message = "Giá biến thể phải lớn hơn hoặc bằng 0")
        Double price,

        @Schema(description = "Số lượng tồn kho", example = "100")
        @NotNull(message = "Số lượng tồn kho không được null")
        @Min(value = 0, message = "Số lượng tồn kho phải lớn hơn hoặc bằng 0")
        Integer stock,

        @Schema(description = "Danh sách thuộc tính của biến thể")
        List<ProductAttributeRequest> attributes
) {
}