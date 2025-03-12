package com.microservice.ecommerce.controller;

import com.microservice.ecommerce.constant.Endpoint;
import com.microservice.ecommerce.model.dto.request.PaymentVNPayRequest;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.momo.PaymentRequest;
import com.microservice.ecommerce.model.momo.PaymentResponse;
import com.microservice.ecommerce.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    12/03/2025 at 2:03 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
@RequestMapping(Endpoint.Payment.PREFIX)
public class PaymentController {
    PaymentService paymentService;

    @PostMapping(Endpoint.Payment.MOMO)
    public ResponseEntity<GlobalResponse<PaymentResponse>> createPayment(
    ) throws Exception {
        return ResponseEntity
                .ok(paymentService.saveMoMoPayment(null));
    }

    @PostMapping(Endpoint.Payment.VN_PAY)
    public ResponseEntity<GlobalResponse<Map<String, String>>> createPayment(
            @RequestBody PaymentVNPayRequest request,
            HttpServletRequest httpServletRequest
    ) {
        return ResponseEntity.ok(
                paymentService.saveVNPayPayment(request, httpServletRequest)
        );
    }

    @GetMapping(Endpoint.Payment.VN_PAY_CALLBACK)
    public ResponseEntity<Void> handleVNPayCallback(
            @RequestParam Map<String, String> requestParams
    ) {
        log.info("Received VNPAY callback: {}", requestParams);
        return ResponseEntity.ok(null);
    }
}
