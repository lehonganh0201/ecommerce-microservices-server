package com.microservice.ecommerce.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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

@Getter
@Setter
@Component
@PropertySource("classpath:environment.properties")
public class PartnerInfo {

    private String accessKey;
    private String partnerCode;
    private String secretKey;

    public PartnerInfo(@Value("${DEV_ACCESS_KEY}") String accessKey,
                       @Value("${DEV_PARTNER_CODE}") String partnerCode,
                       @Value("${DEV_SECRET_KEY}") String secretKey) {
        this.accessKey = accessKey;
        this.partnerCode = partnerCode;
        this.secretKey = secretKey;
    }
}