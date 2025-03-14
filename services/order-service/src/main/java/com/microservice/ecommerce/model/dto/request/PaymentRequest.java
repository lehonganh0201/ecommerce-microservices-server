package com.microservice.ecommerce.model.dto.request;

import com.microservice.ecommerce.constant.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    13/03/2025 at 7:43 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record PaymentRequest(
        @NotNull(message = "Số tiền không được để trống")
        @Positive(message = "Số tiền phải lớn hơn 0")
        Double amount,

        @NotNull(message = "Phương thức thanh toán không được để trống")
        PaymentMethod paymentMethod,

        @NotNull(message = "Mã đơn hàng không được để trống")
        UUID orderId,
        String bankCode,
        String language
) {
}
