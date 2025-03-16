package com.microservice.ecommerce.constant;

import lombok.Getter;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    16/03/2025 at 2:29 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Getter
public enum AttributeType {
    COLOR("color"),
    SIZE("size"),
    MATERIAL("material"),  // Chất liệu (cotton, da, polyester, ...)
    STORAGE("storage"),    // Dung lượng (128GB, 256GB, ...)
    RAM("ram"),            // Bộ nhớ RAM (8GB, 16GB, ...)
    WEIGHT("weight");      // Trọng lượng (500g, 1kg, ...)

    private final String value;

    AttributeType(String value) {
        this.value = value;
    }
}