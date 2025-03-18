package com.microservice.ecommerce.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 8:01 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Schema(description = "Yêu cầu thông tin người dùng")
public record UserRequest(
        @Schema(description = "Số điện thoại người dùng", example = "0987654321")
        @NotBlank(message = "Phone number cannot be blank")
        String phoneNumber,

        @Schema(description = "Thông tin địa chỉ người dùng")
        @Valid
        AddressRequest address
) {
}
