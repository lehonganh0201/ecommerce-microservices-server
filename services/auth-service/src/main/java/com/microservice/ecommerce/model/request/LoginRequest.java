package com.microservice.ecommerce.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 6:52 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record LoginRequest(
        @NotBlank(message = "Username không được để trống")
        @Size(min = 5, max = 20, message = "Username phải từ 5 đến 20 ký tự")
        String username,

        @NotBlank(message = "Password không được để trống")
        @Size(min = 8, message = "Password phải có ít nhất 8 ký tự")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
                message = "Password phải chứa ít nhất 1 chữ hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt")
        String password
) {
}
