package com.microservice.ecommerce.config;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    11/03/2025 at 1:12 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public class MoMoEndpoint {
    private String endpoint;
    private String create;
    private String refund;
    private String query;
    private String confirm;
    private String tokenPay;
    private String tokenBind;
    private String tokenCbInquiry;
    private String tokenDelete;

    public MoMoEndpoint(String endpoint, String create, String refund, String query, String confirm, String tokenPay, String tokenBind, String tokenQueryCb, String tokenDelete) {
        this.endpoint = endpoint;
        this.create = create;
        this.confirm = confirm;
        this.refund = refund;
        this.query = query;
        this.tokenPay = tokenPay;
        this.tokenBind = tokenBind;
        this.tokenCbInquiry = tokenQueryCb;
        this.tokenDelete = tokenDelete;
    }

    public String getCreateUrl() {
        return endpoint + create;
    }

    public String getRefundUrl() {
        return endpoint + refund;
    }

    public String getQueryUrl() {
        return endpoint + query;
    }

    public String getConfirmUrl() {
        return endpoint + confirm;
    }

    public String getTokenPayUrl() {
        return endpoint + tokenPay;
    }

    public String getTokenBindUrl() {
        return endpoint + tokenBind;
    }

    public String getCbTokenInquiryUrl() {
        return endpoint + tokenCbInquiry;
    }

    public String getTokenDeleteUrl() {
        return endpoint + tokenDelete;
    }
}