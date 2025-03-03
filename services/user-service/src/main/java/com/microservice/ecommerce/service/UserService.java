package com.microservice.ecommerce.service;

import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.request.UserRequest;
import com.microservice.ecommerce.model.response.UserResponse;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 7:44 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface UserService {
    GlobalResponse<UserResponse> createUser(UserRequest request, Jwt jwt);

    GlobalResponse<UserResponse> findCurrentUser(Jwt jwt);
}
