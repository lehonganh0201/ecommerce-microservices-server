package com.microservice.ecommerce.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 8:01 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Schema(description = "Yêu cầu tạo người dùng")
public record UserRequest(
        @Schema(description = "Số điện thoại người dùng", example = "0987654321")
        @NotBlank(message = "Số điện thoại không được để trống")
        @Pattern(regexp = "^(\\+84|0)[1-9]\\d{8}$", message = "Số điện thoại không hợp lệ")
        String phoneNumber,

        @Schema(description = "Giới tính của người dùng", example = "true")
        Boolean gender,

        @Schema(description = "Ngày sinh của người dùng", example = "1990-01-01")
        @Past(message = "Ngày sinh phải là ngày trong quá khứ")
        LocalDate dateOfBirth,

        @Schema(description = "Thông tin địa chỉ mặc định của người dùng")
        @Valid
        AddressRequest address
) {
}