package com.microservice.ecommerce.service;

import com.microservice.ecommerce.model.dto.request.CartRequest;
import com.microservice.ecommerce.model.dto.response.CartResponse;
import com.microservice.ecommerce.model.entity.CartItem;
import com.microservice.ecommerce.model.global.GlobalResponse;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    16/03/2025 at 2:50 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface CartService {
    GlobalResponse<CartResponse> addToCart(CartRequest cartRequest, UUID userId);
    GlobalResponse<CartResponse> getCart(UUID userId);
    GlobalResponse<CartResponse> removeItem(UUID userId, UUID variantId);
    GlobalResponse<String> clearCart(UUID userId);
    GlobalResponse<CartResponse> updateQuantity(UUID userId, UUID variantId, int quantity);
}
