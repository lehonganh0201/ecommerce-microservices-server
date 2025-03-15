package com.microservice.ecommerce.client;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    15/03/2025 at 4:27 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record PurchaseRequest(
        List<UUID> variantIds,
        Map<UUID, Integer> orderedQuantities
) {
}
