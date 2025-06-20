package com.microservice.ecommerce.constant;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    09/03/2025 at 5:30 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface Endpoint {
    String PREFIX = "/api/v1";

    public interface Order {
        String PREFIX = Endpoint.PREFIX + "/orders";
        String ORDER_ID = "/{orderId}";
        String REFERENCE = "/reference";
        String FIND_ALL = "/all";
        String CONFIRM = "/confirm";
    }

    public interface Variant {
        String CHECK_STOCK = "/check-stock";
        String UPDATE_STOCK = "/update-stock";
        String GET_PRICE = "/get-prices";
    }

    public interface Payment {
        String PREFIX = "";
        String MOMO = "/momo";
        String VN_PAY = "/vn-pay";
        String VN_PAY_CALLBACK = "/callback";
        String MOMO_NOTIFY = "/notify";
        String MOMO_CALLBACK = "/return";
        String CALLBACK = "/callback";
    }
}
