package com.microservice.ecommerce.client;

import com.microservice.ecommerce.constant.Endpoint;
import com.microservice.ecommerce.model.global.GlobalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    05/06/2025 at 10:34 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@FeignClient(name = "user-service", url = "${application.config.user-service-url}")
@Component
public interface UserServiceClient {
    @PostMapping()
    ResponseEntity<GlobalResponse<UserResponse>> createUser(@RequestBody UserRequest request, @RequestHeader("Authorization") String authorization);
}
