package com.microservice.ecommerce.client;

import com.microservice.ecommerce.constant.Endpoint;
import com.microservice.ecommerce.model.dto.request.PaymentRequest;
import com.microservice.ecommerce.model.dto.response.PaymentResponse;
import com.microservice.ecommerce.model.global.GlobalResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    13/03/2025 at 9:39 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@FeignClient(name = "payment-service", url = "${application.config.payment-service-url}")
@Component
public interface PaymentClient {

    @PostMapping(Endpoint.Payment.MOMO)
    ResponseEntity<GlobalResponse<PaymentResponse>> createMoMoPayment(@RequestBody @Valid PaymentRequest request);

    @PostMapping(Endpoint.Payment.VN_PAY)
    ResponseEntity<GlobalResponse<PaymentResponse>> createVNPayPayment(@RequestBody @Valid PaymentRequest request);

    @PostMapping(Endpoint.Payment.PREFIX)
    ResponseEntity<GlobalResponse<PaymentResponse>> createCODPayment(@RequestBody @Valid PaymentRequest request);
}
