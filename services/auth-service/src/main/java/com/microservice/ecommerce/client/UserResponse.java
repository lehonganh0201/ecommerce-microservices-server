package com.microservice.ecommerce.client;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    05/06/2025 at 10:42 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


@Schema(description = "Phản hồi thông tin người dùng")
public record UserResponse(
        @Schema(description = "Tên đầy đủ của người dùng", example = "Nguyen Van A")
        String fullName,

        @Schema(description = "Số điện thoại người dùng", example = "+84987654321")
        String phoneNumber,

        @Schema(description = "URL ảnh đại diện", example = "https://res.cloudinary.com/demo/image/upload/v1234567890/avatar.jpg")
        String avatarUrl,

        @Schema(description = "Giới tính", example = "true")
        Boolean gender,

        @Schema(description = "Ngày sinh", example = "1990-01-01")
        LocalDate dateOfBirth
) {
}
