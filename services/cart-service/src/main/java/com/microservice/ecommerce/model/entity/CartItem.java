package com.microservice.ecommerce.model.entity;

import com.microservice.ecommerce.client.ProductAttribute;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    16/03/2025 at 2:27 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItem {
    UUID variantId;
    String productName;
    int quantity;
    double price;
    String imageUrl;
    List<ProductAttribute> attributes;
}
