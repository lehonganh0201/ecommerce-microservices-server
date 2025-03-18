package com.microservice.ecommerce.model.request;

import com.microservice.ecommerce.constant.AttributeType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    05/03/2025 at 10:45 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Schema(description = "Yêu cầu thuộc tính sản phẩm")
public record ProductAttributeRequest(
        @Schema(description = "Loại thuộc tính", example = "COLOR, SIZE")
        @NotNull(message = "Loại thuộc tính không được null")
        AttributeType type,

        @Schema(description = "Giá trị thuộc tính", example = "Red, XL")
        @NotBlank(message = "Giá trị thuộc tính không được để trống")
        String value
) {
}