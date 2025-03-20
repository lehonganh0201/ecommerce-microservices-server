package com.microservice.ecommerce.controller;

import com.microservice.ecommerce.constant.Endpoint;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.request.AuthRequest;
import com.microservice.ecommerce.model.request.ChangePasswordRequest;
import com.microservice.ecommerce.model.request.LoginRequest;
import com.microservice.ecommerce.model.response.TokenResponse;
import com.microservice.ecommerce.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @Operation(
            summary = "Đăng ký tài khoản mới",
            description = "Tạo tài khoản mới với thông tin đăng ký từ AuthRequest."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Đăng ký thành công",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
            @ApiResponse(responseCode = "400", description = "Yêu cầu không hợp lệ", content = @Content),
            @ApiResponse(responseCode = "500", description = "Lỗi server", content = @Content)
    })
    @PostMapping(Endpoint.Auth.REGISTER)
    public ResponseEntity<GlobalResponse<String>> register(
            @RequestBody @Valid AuthRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Operation(
            summary = "Đăng nhập vào hệ thống",
            description = "Xác thực tài khoản và nhận JWT Token nếu đăng nhập thành công."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Đăng nhập thành công",
                    content = @Content(schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "400", description = "Thông tin đăng nhập không chính xác", content = @Content),
            @ApiResponse(responseCode = "500", description = "Lỗi server", content = @Content)
    })
    @PostMapping(Endpoint.Auth.LOGIN)
    public ResponseEntity<GlobalResponse<TokenResponse>> login(
            @RequestBody @Valid LoginRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping(Endpoint.Auth.LOGIN)
    public ResponseEntity<GlobalResponse<String>> login(HttpServletResponse response) {
        return ResponseEntity
            .ok(authService.loginWithGoogle(response));
    }

    @GetMapping(Endpoint.Auth.CALLBACK)
    public ResponseEntity<GlobalResponse<TokenResponse>> loginWithGoogle(
            @RequestParam("code") String code
    ) {
        return ResponseEntity
                .ok(authService.handleGoogleCallback(code));
    }

    @Operation(
            summary = "Quên mật khẩu",
            description = "Gửi yêu cầu lấy lại mật khẩu với username. Hệ thống sẽ gửi thông tin khôi phục qua email."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Yêu cầu thành công",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy username", content = @Content),
            @ApiResponse(responseCode = "500", description = "Lỗi server", content = @Content)
    })
    @PostMapping(Endpoint.Auth.FORGOT_PASSWORD)
    public ResponseEntity<GlobalResponse<String>> forgotPassword(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        return ResponseEntity.ok(authService.forgotPassword(username));
    }

    @PostMapping(Endpoint.Auth.CHANGE_PASSWORD)
    public ResponseEntity<GlobalResponse<String>> changePasswordSelfService(
            @RequestBody @Valid ChangePasswordRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        GlobalResponse<String> response = authService.changePassword((String) jwt.getClaims().get("preferred_username"), request.currentPassword(), request.newPassword());
        return ResponseEntity.ok(response);
    }
}
