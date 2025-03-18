package com.microservice.ecommerce.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    09/03/2025 at 5:49 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Schema(description = "Yêu cầu thông tin sản phẩm trong đơn hàng")
public record OrderItemRequest(
        @Schema(description = "Mã sản phẩm", example = "123e4567-e89b-12d3-a456-426614174000")
        @NotNull(message = "Mã sản phẩm không được để null")
        UUID productId,

        @Schema(description = "Mã biến thể sản phẩm", example = "123e4567-e89b-12d3-a456-426614174001")
        @NotNull(message = "Mã biến thể sản phẩm không được để null")
        UUID variantId,

        @Schema(description = "Số lượng sản phẩm", example = "2")
        @Min(value = 1, message = "Số lượng tối thiểu phải là 1")
        @NotNull(message = "Số lượng không được để null")
        Integer quantity
) {
}
