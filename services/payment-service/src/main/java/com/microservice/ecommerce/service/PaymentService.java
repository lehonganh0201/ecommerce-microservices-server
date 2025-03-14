package com.microservice.ecommerce.service;

import com.microservice.ecommerce.model.dto.request.PaymentRequest;
import com.microservice.ecommerce.model.dto.response.PaymentResponse;
import com.microservice.ecommerce.model.global.GlobalResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    12/03/2025 at 1:46 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface PaymentService {
    GlobalResponse<PaymentResponse> saveMoMoPayment(PaymentRequest request) throws Exception;

    GlobalResponse<PaymentResponse> saveVNPayPayment(PaymentRequest request, HttpServletRequest httpServletRequest);

    GlobalResponse<PaymentResponse> savePayment(PaymentRequest request);
}
