package com.microservice.ecommerce.controller;

import com.microservice.ecommerce.constant.Endpoint;
import com.microservice.ecommerce.model.request.ProductAttributeRequest;
import com.microservice.ecommerce.service.ProductAttributeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoint.ProductAttribute.PREFIX)
@Tag(name = "Product Attribute", description = "API quản lý thuộc tính sản phẩm")
public class ProductAttributeController {

    private final ProductAttributeService productAttributeService;

    @PostMapping("/{variantId}")
    @Operation(summary = "Tạo mới thuộc tính sản phẩm", description = "Tạo mới một thuộc tính cho biến thể sản phẩm")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tạo thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy biến thể sản phẩm")
    })
    public ResponseEntity<?> createAttribute(
            @PathVariable UUID variantId,
            @Valid @RequestBody ProductAttributeRequest request) {
        return ResponseEntity.ok(productAttributeService.createAttribute(variantId, request));
    }

    @GetMapping(Endpoint.ProductAttribute.ATTRIBUTE_ID)
    @Operation(summary = "Lấy thông tin thuộc tính", description = "Lấy thông tin thuộc tính theo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lấy thông tin thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy thuộc tính")
    })
    public ResponseEntity<?> getAttribute(@PathVariable UUID id) {
        return ResponseEntity.ok(productAttributeService.getAttributeById(id));
    }

    @GetMapping(Endpoint.ProductAttribute.VARIANT)
    @Operation(summary = "Lấy danh sách thuộc tính theo biến thể", description = "Lấy tất cả thuộc tính của một biến thể sản phẩm")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy biến thể")
    })
    public ResponseEntity<?> getAttributesByVariantId(@PathVariable UUID variantId) {
        return ResponseEntity.ok(productAttributeService.getAttributesByVariantId(variantId));
    }

    @PutMapping(Endpoint.ProductAttribute.ATTRIBUTE_ID)
    @Operation(summary = "Cập nhật thuộc tính", description = "Cập nhật thông tin của một thuộc tính sản phẩm")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cập nhật thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy thuộc tính")
    })
    public ResponseEntity<?> updateAttribute(
            @PathVariable UUID id,
            @Valid @RequestBody ProductAttributeRequest request) {
        return ResponseEntity.ok(productAttributeService.updateAttribute(id, request));
    }

    @DeleteMapping(Endpoint.ProductAttribute.ATTRIBUTE_ID)
    @Operation(summary = "Xóa thuộc tính", description = "Xóa một thuộc tính sản phẩm theo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Xóa thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy thuộc tính")
    })
    public ResponseEntity<Void> deleteAttribute(@PathVariable UUID id) {
        productAttributeService.deleteAttribute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(Endpoint.ProductAttribute.SEARCH)
    @Operation(summary = "Tìm kiếm thuộc tính", description = "Tìm kiếm thuộc tính theo loại và giá trị")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tìm kiếm thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ")
    })
    public ResponseEntity<?> findByTypeAndValue(
            @RequestParam String type,
            @RequestParam String value) {
        return ResponseEntity.ok(productAttributeService.findByTypeAndValue(type, value));
    }
}