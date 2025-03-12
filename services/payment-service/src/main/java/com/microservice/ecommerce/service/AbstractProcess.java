package com.microservice.ecommerce.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microservice.ecommerce.config.MoMoEnvironment;
import com.microservice.ecommerce.config.PartnerInfo;
import com.microservice.ecommerce.exception.MoMoException;
import com.microservice.ecommerce.util.Execute;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    12/03/2025 at 1:25 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public abstract class AbstractProcess<T, V> {

    protected PartnerInfo partnerInfo;
    protected MoMoEnvironment environment;
    protected Execute execute = new Execute();

    public AbstractProcess(MoMoEnvironment environment) {
        this.environment = environment;
        this.partnerInfo = environment.getPartnerInfo();
    }

    public static Gson getGson() {
        return new GsonBuilder()
                .disableHtmlEscaping()
                .create();
    }

    public abstract V execute(T request) throws MoMoException;
}