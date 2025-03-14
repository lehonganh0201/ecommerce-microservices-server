package com.microservice.ecommerce.model.momo;

import com.microservice.ecommerce.constant.ConfirmRequestType;
import com.microservice.ecommerce.constant.Language;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    13/03/2025 at 5:54 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public class ConfirmRequest extends Request {
    private Long amount;
    private String description;
    private ConfirmRequestType requestType;
    private String signature;

    public ConfirmRequest(Long amount, String description, ConfirmRequestType requestType, String signature) {
        this.amount = amount;
        this.description = description;
        this.requestType = requestType;
        this.signature = signature;
    }

    public ConfirmRequest(String partnerCode, String orderId, String requestId, Language lang, Long amount, String description, ConfirmRequestType requestType, String signature) {
        super(partnerCode, orderId, requestId, lang);
        this.amount = amount;
        this.description = description;
        this.requestType = requestType;
        this.signature = signature;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ConfirmRequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(ConfirmRequestType requestType) {
        this.requestType = requestType;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}