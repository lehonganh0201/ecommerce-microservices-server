package com.microservice.ecommerce.controller;

import com.microservice.ecommerce.constant.Endpoint;
import com.microservice.ecommerce.model.dto.request.CartRequest;
import com.microservice.ecommerce.model.dto.response.CartResponse;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class CartController {
    CartService cartService;

    @Operation(summary = "Thêm sản phẩm vào giỏ hàng", description = "Thêm sản phẩm dựa trên thông tin trong CartRequest vào giỏ hàng của người dùng.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thêm sản phẩm vào giỏ hàng thành công",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
            @ApiResponse(responseCode = "400", description = "Yêu cầu không hợp lệ", content = @Content),
            @ApiResponse(responseCode = "401", description = "Người dùng chưa xác thực", content = @Content)
    })
    @PostMapping()
    public ResponseEntity<GlobalResponse<CartResponse>> addCartItemToCart(
            @RequestBody @Valid CartRequest cartItem,
            @AuthenticationPrincipal Jwt jwt
    ) {
        log.info("Received CartRequest: {}", cartItem);
        return ResponseEntity.ok(cartService.addToCart(cartItem, UUID.fromString(jwt.getSubject())));
    }

    @Operation(summary = "Lấy thông tin giỏ hàng", description = "Lấy thông tin giỏ hàng của người dùng đã xác thực.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy thông tin giỏ hàng thành công",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
            @ApiResponse(responseCode = "401", description = "Người dùng chưa xác thực", content = @Content)
    })
    @GetMapping()
    public ResponseEntity<GlobalResponse<CartResponse>> getCart(
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity.ok(cartService.getCart(UUID.fromString(jwt.getSubject())));
    }

    @Operation(summary = "Xóa sản phẩm khỏi giỏ hàng", description = "Xóa sản phẩm khỏi giỏ hàng dựa trên variantId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Xóa sản phẩm khỏi giỏ hàng thành công",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sản phẩm trong giỏ hàng", content = @Content),
            @ApiResponse(responseCode = "401", description = "Người dùng chưa xác thực", content = @Content)
    })
    @PostMapping(Endpoint.Variant.VARIANT_ID)
    public ResponseEntity<GlobalResponse<CartResponse>> removeCartItemFromCart(
            @PathVariable(name = "variantId") UUID variantId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity.ok(cartService.removeItem(UUID.fromString(jwt.getSubject()), variantId));
    }

    @Operation(summary = "Cập nhật số lượng sản phẩm trong giỏ hàng", description = "Cập nhật số lượng của một sản phẩm trong giỏ hàng dựa trên variantId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật số lượng thành công",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sản phẩm trong giỏ hàng", content = @Content),
            @ApiResponse(responseCode = "400", description = "Số lượng không hợp lệ", content = @Content),
            @ApiResponse(responseCode = "401", description = "Người dùng chưa xác thực", content = @Content)
    })
    @PutMapping(Endpoint.Variant.VARIANT_ID)
    public ResponseEntity<GlobalResponse<CartResponse>> updateQuantity(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable(name = "variantId") UUID variantId,
            @RequestParam(name = "quantity", required = true) int quantity
    ) {
        return ResponseEntity.ok(cartService.updateQuantity(UUID.fromString(jwt.getSubject()), variantId, quantity));
    }

    @Operation(summary = "Xóa toàn bộ giỏ hàng", description = "Xóa tất cả các sản phẩm trong giỏ hàng của người dùng.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Xóa giỏ hàng thành công",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
            @ApiResponse(responseCode = "401", description = "Người dùng chưa xác thực", content = @Content)
    })
    @DeleteMapping()
    public ResponseEntity<GlobalResponse<String>> clearCart(
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity.ok(cartService.clearCart(UUID.fromString(jwt.getSubject())));
    }
}
