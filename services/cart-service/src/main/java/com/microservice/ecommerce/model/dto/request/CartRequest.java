package com.microservice.ecommerce.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    16/03/2025 at 2:56 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */



@Schema(description = "Yêu cầu thêm sản phẩm vào giỏ hàng")
public record CartRequest(
        @Schema(description = "ID của biến thể sản phẩm", example = "123e4567-e89b-12d3-a456-426614174000")
        @NotNull(message = "ID biến thể sản phẩm không được để trống")
        UUID variantId,

        @Schema(description = "Số lượng sản phẩm muốn thêm vào giỏ hàng", example = "2", minimum = "1")
        @NotNull(message = "Số lượng sản phẩm không được để trống")
        @Min(value = 1, message = "Số lượng sản phẩm phải lớn hơn 0")
        @JsonProperty("quantity")
        Integer quantity
) {
}
