package com.microservice.ecommerce.controller;

import com.microservice.ecommerce.constant.Endpoint;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.request.ProfileRequest;
import com.microservice.ecommerce.model.request.UserRequest;
import com.microservice.ecommerce.model.response.UserResponse;
import com.microservice.ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 7:40 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@RestController
@RequestMapping(Endpoint.User.PREFIX)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping
    public ResponseEntity<GlobalResponse<UserResponse>> createUser(
            @RequestBody @Valid UserRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity
                .ok(userService.createUser(request, jwt));
    }
}
