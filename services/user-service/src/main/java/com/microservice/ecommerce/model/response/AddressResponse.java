package com.microservice.ecommerce.model.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 8:01 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Schema(description = "Phản hồi thông tin địa chỉ")
public record AddressResponse(
        @Schema(description = "ID địa chỉ", example = "1")
        Integer id,

        @Schema(description = "Số điện thoại liên hệ", example = "0987654321")
        String phoneNumber,

        @Schema(description = "Đường phố", example = "123 Đường Láng")
        String street,

        @Schema(description = "Thành phố", example = "Hà Nội")
        String city,

        @Schema(description = "Tiểu bang", example = "")
        String state,

        @Schema(description = "Quốc gia", example = "Việt Nam")
        String country,

        @Schema(description = "Mã bưu điện", example = "100000")
        String zipCode,

        @Schema(description = "Mô tả địa chỉ", example = "Gần chợ đêm")
        String description,

        @Schema(description = "Địa chỉ mặc định", example = "true")
        boolean isDefault
) {
}