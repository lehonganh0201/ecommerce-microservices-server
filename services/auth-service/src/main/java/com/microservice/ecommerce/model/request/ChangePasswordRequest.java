package com.microservice.ecommerce.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    18/03/2025 at 11:46 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Schema(description = "Yêu cầu đổi mật khẩu cho người dùng đang đăng nhập")
public record ChangePasswordRequest(

        @Schema(description = "Mật khẩu (password), phải chứa ít nhất 1 chữ hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt",
                example = "Secure@123", minLength = 8)
        @NotBlank(message = "Current password is required")
        String currentPassword,

        @Schema(description = "Mật khẩu (password), phải chứa ít nhất 1 chữ hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt",
                example = "Secure@123", minLength = 8)
        @NotBlank(message = "Password không được để trống")
        @Size(min = 8, message = "Password phải có ít nhất 8 ký tự")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
                message = "Password phải chứa ít nhất 1 chữ hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt")
        String newPassword
) {
}
