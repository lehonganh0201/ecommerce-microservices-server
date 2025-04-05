package com.microservice.ecommerce.controller;

import com.microservice.ecommerce.constant.Endpoint;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.request.AddressRequest;
import com.microservice.ecommerce.service.AddressService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    05/04/2025 at 10:04 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping(Endpoint.Address.PREFIX)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddressController {
    AddressService addressService;

    @PostMapping
    public ResponseEntity<GlobalResponse<?>> addAddressToUserInfo(
            @RequestBody AddressRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity
                .ok(addressService.addAddress(request, jwt));
    }

    @PutMapping(Endpoint.Address.ADDRESS_ID)
    public ResponseEntity<GlobalResponse<?>> updateAddress(
            @PathVariable(name = "addressId") Integer addressId,
            @RequestBody AddressRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity
                .ok(addressService.updateAddress(addressId, request, jwt));
    }

    @GetMapping
    public ResponseEntity<GlobalResponse<?>> getOwnAddress(
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity
                .ok(addressService.getOwnAddress(jwt));
    }

    @GetMapping(Endpoint.Address.PREFIX)
    public ResponseEntity<GlobalResponse<?>> findById(
            @PathVariable(name = "addressId") Integer addressId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity
                .ok(addressService.getAddressById(addressId, jwt));
    }
}
