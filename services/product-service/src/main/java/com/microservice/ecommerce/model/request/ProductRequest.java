package com.microservice.ecommerce.model.request;

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

public record ProductRequest(
        @NotBlank(message = "Tên sản phẩm không được để trống")
        String name,

        String description,

        @NotNull(message = "Giá sản phẩm không được null")
        @Min(value = 0, message = "Giá sản phẩm phải lớn hơn hoặc bằng 0")
        Double price,

        @NotNull(message = "Số lượng tồn kho không được null")
        @Min(value = 0, message = "Số lượng tồn kho phải lớn hơn hoặc bằng 0")
        Integer stock,

        @NotNull(message = "Danh mục sản phẩm không được null")
        UUID categoryId,

        List<MultipartFile> images
) {
}