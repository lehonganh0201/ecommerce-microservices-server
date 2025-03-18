package com.microservice.ecommerce.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    09/03/2025 at 8:10 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Schema(description = "Yêu cầu đăng nhập vào hệ thống")
public record LoginRequest(
        @Schema(description = "Tên đăng nhập (username) từ 5 đến 20 ký tự", example = "exampleUser")
        @NotBlank(message = "Username không được để trống")
        @Size(min = 5, max = 20, message = "Username phải từ 5 đến 20 ký tự")
        String username,

        @Schema(description = "Mật khẩu (password) có ít nhất 8 ký tự, bao gồm ít nhất 1 chữ hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt",
                example = "Secure@123")
        @NotBlank(message = "Password không được để trống")
        @Size(min = 8, message = "Password phải có ít nhất 8 ký tự")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
                message = "Password phải chứa ít nhất 1 chữ hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt")
        String password
) {
}
