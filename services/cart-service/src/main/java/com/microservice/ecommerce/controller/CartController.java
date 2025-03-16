package com.microservice.ecommerce.controller;

import com.microservice.ecommerce.constant.Endpoint;
import com.microservice.ecommerce.model.dto.request.CartRequest;
import com.microservice.ecommerce.model.dto.response.CartResponse;
import com.microservice.ecommerce.model.entity.CartItem;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.service.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    16/03/2025 at 6:02 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(Endpoint.Cart.PREFIX)
public class CartController {
    CartService cartService;

    @PostMapping()
    public ResponseEntity<GlobalResponse<CartResponse>> addCartItemToCart(
            @RequestBody CartRequest cartItem,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity
                .ok(cartService.addToCart(cartItem, UUID.fromString(jwt.getSubject())));
    }

    @GetMapping()
    public ResponseEntity<GlobalResponse<CartResponse>> getCart(
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity
                .ok(cartService.getCart(UUID.fromString(jwt.getSubject())));
    }

    @PostMapping(Endpoint.Variant.VARIANT_ID)
    public ResponseEntity<GlobalResponse<CartResponse>> removeCartItemFromCart(
            @PathVariable(name = "variantId") UUID variantId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity
                .ok(cartService.removeItem(UUID.fromString(jwt.getSubject()), variantId));
    }

    @PutMapping(Endpoint.Variant.VARIANT_ID)
    public ResponseEntity<GlobalResponse<CartResponse>> updateQuantity(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable(name = "variantId") UUID variantId,
            @RequestParam(name = "quantity", required = true) int quantity
    ) {
        return ResponseEntity
                .ok(cartService.updateQuantity(UUID.fromString(jwt.getSubject()), variantId, quantity));
    }

    @DeleteMapping()
    public ResponseEntity<GlobalResponse<String>> clearCart(
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity
                .ok(cartService.clearCart(UUID.fromString(jwt.getSubject())));
    }
}
