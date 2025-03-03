package com.microservice.ecommerce.controller;

import com.microservice.ecommerce.constant.Endpoint;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.request.AuthRequest;
import com.microservice.ecommerce.model.request.LoginRequest;
import com.microservice.ecommerce.model.response.TokenResponse;
import com.microservice.ecommerce.service.AuthService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 7:02 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping(Endpoint.Auth.PREFIX)
public class AuthController {
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<GlobalResponse<String>> register(
            @RequestBody @Valid AuthRequest request
    ) {
        return ResponseEntity
                .ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<GlobalResponse<TokenResponse>> login(
            @RequestBody @Valid LoginRequest request
    ) {
        return ResponseEntity
                .ok(authService.login(request));
    }
}
