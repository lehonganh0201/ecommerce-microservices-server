package com.microservice.ecommerce.constant;

import lombok.Getter;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    15/03/2025 at 5:17 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public enum EmailTemplate {
    PAYMENT_CONFIRMATION("payment-confirmation.html", "Payment_successfully_processed"),
    ORDER_CONFIRMATION("order-confirmation.html", "Order_confirmation"),
    PAYMENT_FAIL("payment-fail.html", "Payment_fail_processed");

    @Getter
    private final String template;

    @Getter
    private final String subject;

    EmailTemplate(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
}
