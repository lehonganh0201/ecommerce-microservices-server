package com.microservice.ecommerce.constant;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    12/03/2025 at 2:04 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface Endpoint {
    String PREFIX = "/api/v1";
    public interface Payment {
        String PREFIX = Endpoint.PREFIX + "/payments";
        String MOMO = "/momo";
        String VN_PAY = "/vn-pay";
        String CALLBACK = "/callback";
        String MOMO_NOTIFY = "/notify";
        String MOMO_CALLBACK = "/return";
    }
}
