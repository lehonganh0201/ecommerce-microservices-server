package com.microservice.ecommerce.service;

import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.request.AuthRequest;
import com.microservice.ecommerce.model.request.LoginRequest;
import com.microservice.ecommerce.model.response.TokenResponse;
import jakarta.servlet.http.HttpServletResponse;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 6:54 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface AuthService {
    GlobalResponse<String> register(AuthRequest request);

    GlobalResponse<TokenResponse> login(LoginRequest request);

    GlobalResponse<TokenResponse> handleGoogleCallback(String code);

    GlobalResponse<String> forgotPassword(String username);

    GlobalResponse<String> changePassword(String username, String currentPassword, String newPassword);

    GlobalResponse<String> loginWithGoogle(HttpServletResponse response);

    GlobalResponse<TokenResponse> refreshToken(String refreshToken);
}
