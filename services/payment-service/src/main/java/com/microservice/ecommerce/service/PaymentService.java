package com.microservice.ecommerce.service;

import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.momo.PaymentRequest;
import com.microservice.ecommerce.model.momo.PaymentResponse;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    12/03/2025 at 1:46 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface PaymentService {
    GlobalResponse<PaymentResponse> savePayment(PaymentRequest request) throws Exception;
}
