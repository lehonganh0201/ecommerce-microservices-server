package com.microservice.ecommerce.model.dto.request;

import com.microservice.ecommerce.constant.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    09/03/2025 at 5:41 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Schema(description = "Yêu cầu tạo đơn hàng")
public record OrderRequest(
        @Schema(description = "Phương thức thanh toán", example = "MOMO")
        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod,

        @Schema(description = "Địa chỉ của người nhận")
        @NotNull(message = "Address is required")
        Integer addressId,

        @Schema(description = "Ngôn ngữ hiển thị trong giao dịch", example = "vi")
        String language,

        @Schema(description = "Mã ngân hàng nếu thanh toán qua ngân hàng", example = "NCB")
        String bankCode,

        @Schema(description = "Danh sách các sản phẩm trong đơn hàng")
        @NotEmpty(message = "Order must contain at least one item")
        @Valid
        List<OrderItemRequest> items
) {
}
