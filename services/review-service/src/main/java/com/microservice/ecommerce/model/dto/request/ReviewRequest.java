package com.microservice.ecommerce.model.dto.request;

import jakarta.validation.constraints.*;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    24/03/2025 at 1:06 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record ReviewRequest(
        @NotNull(message = "Product ID không được để trống")
        UUID productId,

        @Min(value = 1, message = "Rating tối thiểu là 1")
        @Max(value = 5, message = "Rating tối đa là 5")
        Integer rating,

        @NotBlank(message = "Comment không được để trống")
        @Size(max = 1000, message = "Comment tối đa 1000 ký tự")
        String comment
) {
}
