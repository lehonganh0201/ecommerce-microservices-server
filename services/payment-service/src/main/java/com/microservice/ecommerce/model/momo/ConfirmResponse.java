package com.microservice.ecommerce.model.momo;

import com.microservice.ecommerce.constant.ConfirmRequestType;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    13/03/2025 at 5:55 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public class ConfirmResponse extends Response {
    private Long amount;
    private Long transId;
    private String requestId;
    private ConfirmRequestType requestType;
}