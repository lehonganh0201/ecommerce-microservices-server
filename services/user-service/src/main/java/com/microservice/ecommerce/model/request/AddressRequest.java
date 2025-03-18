package com.microservice.ecommerce.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 8:19 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Schema(description = "Thông tin địa chỉ người dùng")
public record AddressRequest(
        @Schema(description = "Tên đường", example = "123 Trần Hưng Đạo")
        @NotBlank(message = "Street cannot be blank")
        String street,

        @Schema(description = "Thành phố", example = "Hà Nội")
        @NotBlank(message = "City cannot be blank")
        String city,

        @Schema(description = "Bang hoặc tỉnh", example = "Đống Đa")
        @NotBlank(message = "State cannot be blank")
        String state,

        @Schema(description = "Quốc gia", example = "Việt Nam")
        @NotBlank(message = "Country cannot be blank")
        String country,

        @Schema(description = "Mã bưu chính", example = "100000")
        String zipCode,

        @Schema(description = "Mô tả chi tiết địa chỉ", example = "Gần trường Đại học Bách Khoa")
        String description
) {
}