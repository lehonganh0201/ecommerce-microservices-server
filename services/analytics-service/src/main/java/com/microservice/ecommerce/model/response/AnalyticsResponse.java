package com.microservice.ecommerce.model.response;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    23/03/2025 at 10:37 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record AnalyticsResponse<T>(
        T data
) {
}
