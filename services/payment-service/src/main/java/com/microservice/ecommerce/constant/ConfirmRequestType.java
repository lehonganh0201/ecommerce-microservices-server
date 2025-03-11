package com.microservice.ecommerce.constant;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    11/03/2025 at 1:13 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


import com.google.gson.annotations.SerializedName;

public enum ConfirmRequestType {

    /**
     * The capture momo wallet.
     */
    @SerializedName("capture")
    CAPTURE("capture"),

    /**
     * The pay with atm
     */
    @SerializedName("cancel")
    CANCEL("cancel");

    private final String value;

    ConfirmRequestType(String value) {
        this.value = value;
    }

    public static ConfirmRequestType findByName(String name) {
        for (ConfirmRequestType type : values()) {
            if (type.getConfirmRequestType().equals(name)) {
                return type;
            }
        }
        return null;
    }

    public String getConfirmRequestType() {
        return value;
    }
}