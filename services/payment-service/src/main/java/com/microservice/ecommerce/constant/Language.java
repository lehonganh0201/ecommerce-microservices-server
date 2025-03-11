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

public enum Language {

    @SerializedName("vi")
    VI(Constants.LANGUAGE_VI),

    @SerializedName("en")
    EN(Constants.LANGUAGE_EN);

    private final String value;

    Language(String value) {
        this.value = value;
    }

    public static Language findByName(String name) {
        for (Language type : values()) {
            if (type.getLanguage().equals(name)) {
                return type;
            }
        }
        return null;
    }

    public String getLanguage() { return value; }

}