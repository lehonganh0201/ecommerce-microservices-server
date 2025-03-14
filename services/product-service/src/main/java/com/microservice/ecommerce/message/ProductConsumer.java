package com.microservice.ecommerce.message;

import com.microservice.ecommerce.model.request.OrderItemRequest;
import com.microservice.ecommerce.service.ProductVariantService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    13/03/2025 at 10:34 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class ProductConsumer {
    ProductVariantService variantService;

    @KafkaListener(topics = "update-stock-topic", groupId = "product-service-group")
    public void consumeStockUpdate(List<OrderItemRequest> requests) {
        log.info("Nhận sự kiện cập nhật stock: {}", requests);

        // Gọi service để cập nhật số lượng sản phẩm
        variantService.updateStock(requests);
    }
}
