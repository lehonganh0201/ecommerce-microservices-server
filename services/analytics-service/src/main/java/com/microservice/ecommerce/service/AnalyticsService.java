package com.microservice.ecommerce.service;

import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.response.AnalyticsResponse;

import java.time.LocalDate;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    23/03/2025 at 10:52 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface AnalyticsService {
    GlobalResponse<AnalyticsResponse<?>> getRevenueStatistics(LocalDate startDate, LocalDate endDate, String groupBy, Integer interval);
}
