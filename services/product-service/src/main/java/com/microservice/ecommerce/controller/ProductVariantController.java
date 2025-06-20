package com.microservice.ecommerce.controller;

import com.microservice.ecommerce.constant.Endpoint;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.request.OrderItemRequest;
import com.microservice.ecommerce.model.request.ProductVariantRequest;
import com.microservice.ecommerce.model.request.PurchaseRequest;
import com.microservice.ecommerce.model.response.ProductPriceResponse;
import com.microservice.ecommerce.model.response.ProductResponse;
import com.microservice.ecommerce.model.response.ProductVariantResponse;
import com.microservice.ecommerce.service.ProductVariantService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    06/03/2025 at 9:09 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoint.ProductVariant.PREFIX)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class ProductVariantController {
    ProductVariantService variantService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Tạo mới biến thể sản phẩm", description = "Tạo biến thể mới dựa trên thông tin đầu vào")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tạo thành công biến thể sản phẩm",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ", content = @Content),
            @ApiResponse(responseCode = "500", description = "Lỗi server", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GlobalResponse<ProductResponse>> createProductVariantToProduct(
            @ModelAttribute @Valid ProductVariantRequest variantRequest
    ) {
        return ResponseEntity.ok(variantService.createVariantToProduct(variantRequest));
    }

    @PutMapping(value = Endpoint.ProductVariant.VARIANT_ID, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Cập nhật biến thể sản phẩm", description = "Cập nhật thông tin biến thể sản phẩm theo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật thành công",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy biến thể sản phẩm", content = @Content),
            @ApiResponse(responseCode = "500", description = "Lỗi server", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GlobalResponse<ProductResponse>> updateProductVariant(
            @PathVariable(name = "variantId") UUID variantId,
            @ModelAttribute ProductVariantRequest variantRequest
    ) {
        return ResponseEntity.ok(variantService.updateVariantProduct(variantId, variantRequest));
    }

    @DeleteMapping(Endpoint.ProductVariant.VARIANT_ID)
    @Operation(summary = "Xóa biến thể sản phẩm", description = "Xóa biến thể sản phẩm theo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Xóa thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy biến thể sản phẩm", content = @Content),
            @ApiResponse(responseCode = "500", description = "Lỗi server", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GlobalResponse<String>> deleteProductVariantById(
            @PathVariable(name = "variantId") UUID variantId
    ) {
        return ResponseEntity.ok(variantService.deleteProductVariantById(variantId));
    }

    @PostMapping(Endpoint.ProductVariant.UPLOAD)
    @Operation(summary = "Upload ảnh cho biến thể", description = "Upload ảnh cho biến thể sản phẩm theo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Upload thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy biến thể sản phẩm", content = @Content),
            @ApiResponse(responseCode = "500", description = "Lỗi server", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GlobalResponse<String>> uploadImageToVariant(
            @PathVariable(name = "variantId") UUID variantId,
            @RequestParam(name = "image") MultipartFile image
    ) {
        return ResponseEntity.ok(variantService.uploadImageToVariant(variantId, image));
    }

    @Hidden
    @PostMapping(Endpoint.ProductVariant.CHECK_STOCK)
    @Operation(summary = "Kiểm tra tồn kho", description = "Kiểm tra số lượng tồn kho cho các biến thể sản phẩm")
    @ApiResponse(responseCode = "200", description = "Kiểm tra thành công")
    public ResponseEntity<Boolean> checkStock(
            @RequestBody @Valid List<OrderItemRequest> requests
    ) {
        log.info("RECEIVCE CHECK STOCK: {}", requests);
        return ResponseEntity.ok(variantService.checkStock(requests));
    }

    @Hidden
    @PostMapping(Endpoint.ProductVariant.GET_PRICE)
    @Operation(summary = "Lấy giá sản phẩm", description = "Lấy giá của các biến thể sản phẩm dựa trên yêu cầu mua hàng")
    @ApiResponse(responseCode = "200", description = "Lấy giá thành công")
    public ResponseEntity<List<ProductPriceResponse>> getPrices(
            @RequestBody PurchaseRequest request
    ) {
        return ResponseEntity.ok(variantService.getPrices(request));
    }

    @Hidden
    @PutMapping(Endpoint.ProductVariant.UPDATE_STOCK)
    @Operation(summary = "Cập nhật tồn kho", description = "Cập nhật số lượng tồn kho cho các biến thể sản phẩm")
    @ApiResponse(responseCode = "200", description = "Cập nhật thành công")
    public ResponseEntity<Void> updateStock(
            @RequestBody @Valid List<OrderItemRequest> requests
    ) {
        return ResponseEntity.ok(variantService.updateStock(requests));
    }

    @GetMapping(Endpoint.ProductVariant.VARIANT_ID)
    @Operation(summary = "Lấy thông tin biến thể sản phẩm", description = "Lấy thông tin chi tiết của biến thể sản phẩm theo ID")
    @ApiResponse(responseCode = "200", description = "Lấy thành công")
    public ResponseEntity<GlobalResponse<ProductVariantResponse>> getProductVariantById(
            @PathVariable(name = "variantId") UUID variantId
    ) {
        return ResponseEntity.ok(variantService.getProductVariantById(variantId));
    }
}
