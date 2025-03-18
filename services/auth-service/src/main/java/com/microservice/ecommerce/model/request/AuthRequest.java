package com.microservice.ecommerce.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    09/03/2025 at 7:55 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Schema(description = "Yêu cầu đăng ký tài khoản người dùng")
public record AuthRequest(
        @Schema(description = "Tên đăng nhập (username), phải từ 5 đến 20 ký tự", example = "new_user123")
        @NotBlank(message = "Username không được để trống")
        @Size(min = 5, max = 20, message = "Username phải từ 5 đến 20 ký tự")
        String username,

        @Schema(description = "Mật khẩu (password), phải chứa ít nhất 1 chữ hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt",
                example = "Secure@123", minLength = 8)
        @NotBlank(message = "Password không được để trống")
        @Size(min = 8, message = "Password phải có ít nhất 8 ký tự")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
                message = "Password phải chứa ít nhất 1 chữ hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt")
        String password,

        @Schema(description = "Địa chỉ email hợp lệ của người dùng", example = "user@example.com")
        @NotBlank(message = "Email không được để trống")
        @Email(message = "Email không hợp lệ")
        String email,

        @Schema(description = "Tên của người dùng", example = "John")
        @NotBlank(message = "First name không được để trống")
        String firstName,

        @Schema(description = "Họ của người dùng", example = "Doe")
        @NotBlank(message = "Last name không được để trống")
        String lastName
) {
}