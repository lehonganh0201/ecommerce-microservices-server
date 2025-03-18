package com.microservice.ecommerce.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 9:09 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Schema(description = "Yêu cầu cập nhật thông tin hồ sơ cá nhân")
public record ProfileRequest(
        @Schema(description = "Ảnh đại diện của người dùng", type = "string", format = "binary")
        MultipartFile avatar,

        @Schema(description = "Giới tính của người dùng (true: nam, false: nữ)", example = "true")
        Boolean gender,

        @Schema(description = "Ngày sinh của người dùng", example = "2000-01-01T00:00:00")
        LocalDateTime dateOfBirth
) {
}
