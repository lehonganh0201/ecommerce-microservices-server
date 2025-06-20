package com.microservice.ecommerce.client;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    05/06/2025 at 10:53 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record AddressRequest(
        @Schema(description = "Số điện thoại liên hệ cho địa chỉ", example = "0987654321")
        @Pattern(regexp = "^0[1-9]\\d{8}$", message = "Số điện thoại không hợp lệ")
        String phoneNumber,

        @Schema(description = "Đường phố", example = "123 Đường Láng")
        @NotBlank(message = "Đường phố không được để trống")
        String street,

        @Schema(description = "Thành phố", example = "Hà Nội")
        @NotBlank(message = "Thành phố không được để trống")
        String city,

        @Schema(description = "Tiểu bang (nếu có)", example = "")
        String state,

        @Schema(description = "Quốc gia", example = "Việt Nam")
        @NotBlank(message = "Quốc gia không được để trống")
        String country,

        @Schema(description = "Mã bưu điện", example = "100000")
        String zipCode,

        @Schema(description = "Mô tả địa chỉ", example = "Gần chợ đêm")
        String description,

        @Schema(description = "Địa chỉ mặc định", example = "true")
        Boolean isDefault
) {
}
