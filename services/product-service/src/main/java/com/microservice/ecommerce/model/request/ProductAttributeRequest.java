package com.microservice.ecommerce.model.request;

import com.microservice.ecommerce.constant.AttributeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    05/03/2025 at 10:45 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record ProductAttributeRequest(
        @NotNull(message = "Loại thuộc tính không được null")
        AttributeType type,

        @NotBlank(message = "Giá trị thuộc tính không được để trống")
        String value
) {
}
