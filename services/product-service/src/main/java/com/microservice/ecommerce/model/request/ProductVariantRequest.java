package com.microservice.ecommerce.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    05/03/2025 at 10:41 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record ProductVariantRequest(
        @NotNull(message = "Giá biến thể không được null")
        @Min(value = 0, message = "Giá biến thể phải lớn hơn hoặc bằng 0")
        Double price,

        @NotNull(message = "Số lượng tồn kho không được null")
        @Min(value = 0, message = "Số lượng tồn kho phải lớn hơn hoặc bằng 0")
        Integer stock,

        List<ProductAttributeRequest> attributes
) {
}
