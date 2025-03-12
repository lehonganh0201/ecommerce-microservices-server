package com.microservice.ecommerce.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    11/03/2025 at 1:12 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Component
@PropertySource("classpath:environment.properties")
public class MoMoEndpoint {
    private final String endpoint;
    private final String create;
    private final String refund;
    private final String query;
    private final String confirm;
    private final String tokenPay;
    private final String tokenBind;
    private final String tokenCbInquiry;
    private final String tokenDelete;

    public MoMoEndpoint(@Value("${DEV_MOMO_ENDPOINT}") String endpoint,
                        @Value("${CREATE_URL}") String create,
                        @Value("${REFUND_URL}") String refund,
                        @Value("${QUERY_URL}") String query,
                        @Value("${CONFIRM_URL}") String confirm,
                        @Value("${TOKEN_PAY_URL}") String tokenPay,
                        @Value("${TOKEN_BIND_URL}") String tokenBind,
                        @Value("${TOKEN_INQUIRY_URL}") String tokenCbInquiry,
                        @Value("${TOKEN_DELETE_URL}") String tokenDelete
    ) {
        this.endpoint = endpoint;
        this.create = create;
        this.refund = refund;
        this.query = query;
        this.confirm = confirm;
        this.tokenPay = tokenPay;
        this.tokenBind = tokenBind;
        this.tokenCbInquiry = tokenCbInquiry;
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