package com.microservice.ecommerce.controller;

import com.microservice.ecommerce.constant.Endpoint;
import com.microservice.ecommerce.model.dto.request.OrderRequest;
import com.microservice.ecommerce.model.dto.response.OrderResponse;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.PageResponse;
import com.microservice.ecommerce.service.OrderService;
import io.swagger.v3.oas.annotations.Hidden;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    09/03/2025 at 5:29 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoint.Order.PREFIX)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class OrderController {
    OrderService orderService;

    @Operation(summary = "Tạo đơn hàng mới", description = "Tạo đơn hàng dựa trên OrderRequest của người dùng đã xác thực.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tạo đơn hàng thành công",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ", content = @Content),
            @ApiResponse(responseCode = "401", description = "Người dùng chưa xác thực", content = @Content)
    })
    @PostMapping()
    public ResponseEntity<GlobalResponse<OrderResponse>> createOrder(
            @RequestBody @Valid OrderRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        log.info("Received OrderRequest: {}", request);
        return ResponseEntity.ok(orderService.createOrder(request, jwt));
    }

    @Operation(summary = "Lấy danh sách đơn hàng của người dùng", description = "Lấy danh sách các đơn hàng dựa trên trạng thái orderType.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
            @ApiResponse(responseCode = "401", description = "Người dùng chưa xác thực", content = @Content)
    })
    @GetMapping()
    public ResponseEntity<GlobalResponse<List<OrderResponse>>> getOwnOrders(
            @RequestParam(name = "orderType", required = false) String type,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity.ok(orderService.findOwnOrders(type, jwt));
    }

    @Operation(summary = "Lấy chi tiết đơn hàng theo ID", description = "Lấy chi tiết đơn hàng dựa trên orderId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy thông tin đơn hàng thành công",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy đơn hàng", content = @Content),
            @ApiResponse(responseCode = "401", description = "Người dùng chưa xác thực", content = @Content)
    })
    @Hidden
    @GetMapping(Endpoint.Order.ORDER_ID)
    public ResponseEntity<GlobalResponse<OrderResponse>> getOrderById(
            @PathVariable(name = "orderId") UUID orderId
    ) {
        return ResponseEntity.ok(orderService.findOrderById(orderId));
    }

    @Operation(summary = "Thay đổi trạng thái đơn hàng", description = "Thay đổi trạng thái đơn hàng dựa trên orderId và trạng thái orderStatus.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hủy đơn hàng thành công",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy đơn hàng", content = @Content),
            @ApiResponse(responseCode = "400", description = "Trạng thái không hợp lệ", content = @Content),
            @ApiResponse(responseCode = "401", description = "Người dùng chưa xác thực", content = @Content)
    })
    @Hidden
    @PutMapping(Endpoint.Order.ORDER_ID)
    public ResponseEntity<GlobalResponse<OrderResponse>> changeOrderStatus(
            @PathVariable(name = "orderId") UUID orderId,
            @RequestParam(name = "status") String orderStatus
    ) {
        return ResponseEntity.ok(orderService.changeOrderStatus(orderId, orderStatus));
    }

    @GetMapping(Endpoint.Order.REFERENCE)
    public ResponseEntity<GlobalResponse<OrderResponse>> getOrderByReference(
            @RequestParam(name = "reference") String reference,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity.ok(orderService.getByReference(reference, jwt));
    }

    @GetMapping(Endpoint.Order.FIND_ALL)
    public ResponseEntity<GlobalResponse<PageResponse<OrderResponse>>> findAllOrderResponse(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sortedBy", required = false) String sortedBy,
            @RequestParam(name = "sortDirection", required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "customerId", required = false) String customerId,
            @RequestParam(name = "paymentMethod", required = false) String paymentMethod,
            @RequestParam(name = "minTotal", required = false) Double minTotal,
            @RequestParam(name = "maxTotal", required = false) Double maxTotal,
            @RequestParam(name = "productId", required = false) String productId,
            @RequestParam(name = "deliveryMethod", required = false) String deliveryMethod,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(orderService.findAllOrders(
                page, size, sortedBy, sortDirection, status,
                customerId, paymentMethod, minTotal, maxTotal,
                productId, deliveryMethod, startDate, endDate
        ));
    }
}
