package com.microservice.ecommerce.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.ecommerce.client.ProductClient;
import com.microservice.ecommerce.client.ProductResponse;
import com.microservice.ecommerce.exception.BussinessException;
import com.microservice.ecommerce.model.dto.request.CartRequest;
import com.microservice.ecommerce.model.dto.response.CartResponse;
import com.microservice.ecommerce.model.entity.Cart;
import com.microservice.ecommerce.model.entity.CartItem;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.Status;
import com.microservice.ecommerce.service.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    16/03/2025 at 5:31 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class CartServiceImpl implements CartService {
    RedisTemplate<String, Object> redisTemplate;
    ProductClient productClient;

    ObjectMapper objectMapper;

    private static final String CART_PREFIX = "cart:";

    @Override
    public GlobalResponse<CartResponse> addToCart(CartRequest cartRequest, UUID userId) {
        String cartKey = CART_PREFIX + userId;

        Cart cart = objectMapper.convertValue(redisTemplate.opsForValue().get(cartKey), Cart.class);

        if (cart == null) {
            cart = new Cart();
            cart.setUserID(userId);
            cart.setLastUpdated(LocalDateTime.now());
            cart.setItems(new ArrayList<>());
        }

        if (cartRequest.quantity() <= 0) {
            throw new BussinessException("Số lượng sản phẩm phải lớn hơn 0");
        }

        ProductResponse variant = productClient.findProductVariantById(cartRequest.variantId()).getBody().data();

        if (variant.stock() < cartRequest.quantity()) {
            throw new BussinessException("Số lượng sản phẩm không đủ trong kho");
        }

        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getVariantId().equals(cartRequest.variantId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + cartRequest.quantity());
        } else {
            cart.getItems().add(CartItem.builder()
                    .variantId(cartRequest.variantId())
                    .quantity(cartRequest.quantity())
                    .imageUrl(variant.imageUrl())
                    .attributes(variant.attributes())
                    .price(variant.price())
                    .productName(variant.productName())
                    .build());
        }

        cart.setLastUpdated(LocalDateTime.now());
        redisTemplate.opsForValue().set(cartKey, cart);

        return new GlobalResponse<>(
                Status.SUCCESS,
                buildCartResponse(cart)
        );
    }

    @Override
    public GlobalResponse<CartResponse> getCart(UUID userId) {
        String cartKey = CART_PREFIX + userId;
        Object cachedCart = redisTemplate.opsForValue().get(cartKey);

        if (cachedCart == null) {
            throw new BussinessException("Giỏ hàng trống");
        }

        Cart cart = objectMapper.convertValue(cachedCart, Cart.class);

        return new GlobalResponse<>(Status.SUCCESS, buildCartResponse(cart));
    }

    @Override
    public GlobalResponse<CartResponse> removeItem(UUID userId, UUID variantId) {
        String cartKey = CART_PREFIX + userId;
        Object rawCart = redisTemplate.opsForValue().get(cartKey);

        if (rawCart == null) {
            throw new BussinessException("Giỏ hàng trống");
        }

        Cart cart;
        if (rawCart instanceof LinkedHashMap) {
            cart = objectMapper.convertValue(rawCart, Cart.class);
        } else if (rawCart instanceof Cart) {
            cart = (Cart) rawCart;
        } else {
            throw new BussinessException("Dữ liệu giỏ hàng không hợp lệ");
        }

        boolean removed = cart.getItems().removeIf(item -> item.getVariantId().equals(variantId));

        if (!removed) {
            throw new BussinessException("Sản phẩm không tồn tại trong giỏ hàng");
        }

        cart.setLastUpdated(LocalDateTime.now());
        redisTemplate.opsForValue().set(cartKey, cart);

        return new GlobalResponse<>(Status.SUCCESS, buildCartResponse(cart));
    }

    @Override
    public GlobalResponse<String> clearCart(UUID userId) {
        String cartKey = CART_PREFIX + userId;
        redisTemplate.delete(cartKey);
        return new GlobalResponse<>(Status.SUCCESS, "Đã xóa giỏ hàng thành công");
    }

    @Override
    public GlobalResponse<CartResponse> updateQuantity(UUID userId, UUID variantId, int quantity) {
        if (quantity <= 0) {
            throw new BussinessException("Số lượng sản phẩm phải lớn hơn 0");
        }

        String cartKey = CART_PREFIX + userId;
        Cart cart = objectMapper.convertValue(redisTemplate.opsForValue().get(cartKey), Cart.class);

        if (cart == null) {
            throw new BussinessException("Giỏ hàng trống");
        }

        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getVariantId().equals(variantId))
                .findFirst()
                .orElse(null);

        if (existingItem == null) {
            throw new BussinessException("Sản phẩm không tồn tại trong giỏ hàng");
        }

        ProductResponse variant = productClient.findProductVariantById(variantId).getBody().data();

        if (variant.stock() < quantity) {
            throw new BussinessException("Số lượng sản phẩm không đủ trong kho");
        }

        existingItem.setQuantity(quantity);
        cart.setLastUpdated(LocalDateTime.now());
        redisTemplate.opsForValue().set(cartKey, cart);

        return new GlobalResponse<>(Status.SUCCESS, buildCartResponse(cart));
    }

    private CartResponse buildCartResponse(Cart cart) {
        return CartResponse.builder()
                .lastUpdated(cart.getLastUpdated())
                .items(cart.getItems())
                .build();
    }
}