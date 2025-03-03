package com.microservice.ecommerce.constant;

import lombok.Getter;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 8:38 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public enum RoleType {
    ADMIN("ROLE_ADMIN"),
    SELLER("ROLE_PM"),
    CUSTOMER("ROLE_CUSTOMER");

    @Getter
    private final String name;

    RoleType(String name) {
        this.name = name;
    }
}
