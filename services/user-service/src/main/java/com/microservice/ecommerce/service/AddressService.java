package com.microservice.ecommerce.service;

import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.request.AddressRequest;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    05/04/2025 at 10:05 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface AddressService {
    GlobalResponse<?> addAddress(AddressRequest request, Jwt jwt);

    GlobalResponse<?> updateAddress(Integer addressId, AddressRequest request, Jwt jwt);

    GlobalResponse<?> getOwnAddress(Jwt jwt);

    GlobalResponse<?> getAddressById(Integer addressId, Jwt jwt);
}
