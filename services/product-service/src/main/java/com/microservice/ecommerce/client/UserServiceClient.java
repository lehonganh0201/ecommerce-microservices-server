package com.microservice.ecommerce.client;

import com.microservice.ecommerce.model.global.GlobalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    06/03/2025 at 1:41 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@FeignClient(name = "user-service",
            url = "${application.config.user-service-url}")
public interface UserServiceClient {

    @GetMapping("/me")
    ResponseEntity<GlobalResponse<UserResponse>> checkUserProfile(@RequestHeader("Authorization") String token);
}
