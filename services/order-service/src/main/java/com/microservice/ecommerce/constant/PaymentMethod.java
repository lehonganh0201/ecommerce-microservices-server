package com.microservice.ecommerce.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    09/03/2025 at 5:03 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public enum PaymentMethod {
    MOMO,
    VN_PAY,
    COD; // Cash on delivery

    @JsonCreator
    public static PaymentMethod fromString(String value) {
        return PaymentMethod.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toString() {
        return this.name();
    }
}
