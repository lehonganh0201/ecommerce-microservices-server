package com.microservice.ecommerce.config;

import com.microservice.ecommerce.job.FeignClientInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    14/03/2025 at 9:47 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Configuration
public class FeignClientConfig {
    @Bean
    public RequestInterceptor feignClientInterceptor() {
        return new FeignClientInterceptor();
    }
}
