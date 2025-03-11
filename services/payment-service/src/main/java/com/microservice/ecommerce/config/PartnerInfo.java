package com.microservice.ecommerce.config;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    11/03/2025 at 1:12 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public class PartnerInfo {

    private String accessKey;
    private String partnerCode;
    private String secretKey;
    private String publicKey;

    public PartnerInfo(String partnerCode, String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.partnerCode = partnerCode;
        this.secretKey = secretKey;
    }

    public PartnerInfo(String partnerCode, String accessKey, String secretKey, String publicKey) {
        this.accessKey = accessKey;
        this.partnerCode = partnerCode;
        this.secretKey = secretKey;
        this.publicKey = publicKey;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPublicKey() {
        return publicKey;
    }
}