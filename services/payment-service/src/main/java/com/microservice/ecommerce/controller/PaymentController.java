package com.microservice.ecommerce.controller;

import com.microservice.ecommerce.constant.Endpoint;
import com.microservice.ecommerce.model.dto.request.PaymentRequest;
import com.microservice.ecommerce.model.dto.response.PaymentResponse;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
            @RequestBody @Valid PaymentRequest request
    ) throws Exception {
        return ResponseEntity
                .ok(paymentService.saveMoMoPayment(request));
    }

    @PostMapping()
    public ResponseEntity<GlobalResponse<PaymentResponse>> createCODPayment(
            @RequestBody @Valid PaymentRequest request
    ) {
        return ResponseEntity
                .ok(paymentService.savePayment(request));
    }

    @PostMapping(Endpoint.Payment.VN_PAY)
    public ResponseEntity<GlobalResponse<PaymentResponse>> createPayment(
            @RequestBody @Valid PaymentRequest request,
            HttpServletRequest httpServletRequest
    ) {
        return ResponseEntity.ok(
                paymentService.saveVNPayPayment(request, httpServletRequest)
        );
    }

    @GetMapping(Endpoint.Payment.CALLBACK)
    public ResponseEntity<GlobalResponse<String>> handleVNPayCallback(
            @RequestParam Map<String, String> requestParams
            ) {
        log.info("Received transaction callback: {}", requestParams);
        return ResponseEntity.ok(paymentService.paymentConfirmation(requestParams));
    }
//
//    @PostMapping(Endpoint.Payment.MOMO_NOTIFY)
//    public ResponseEntity<Void> handleMoMoCallback(
//            @RequestBody Map<String, Object> requestBody
//    ) {
//        log.info("Received MOMO callback: {}", requestBody);
//
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping(Endpoint.Payment.MOMO_CALLBACK)
//    public ResponseEntity<String> handleReturnUrl(@RequestParam Map<String, String> params) {
//        String status = params.get("resultCode"); // MoMo trả về mã trạng thái
//        String orderId = params.get("orderId");
//
//        log.info("Received MOMO callback return URL: {}", params);
//
//        if ("0".equals(status)) { // "0" là thành công
//            return ResponseEntity.ok("Thanh toán thành công! Order ID: " + orderId);
//        } else {
//            return ResponseEntity.ok("Thanh toán thất bại. Mã lỗi: " + status);
//        }
//    }
}
