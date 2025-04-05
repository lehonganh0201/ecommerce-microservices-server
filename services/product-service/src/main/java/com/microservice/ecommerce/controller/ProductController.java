package com.microservice.ecommerce.controller;

import com.microservice.ecommerce.constant.Endpoint;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.PageResponse;
import com.microservice.ecommerce.model.request.ProductRequest;
import com.microservice.ecommerce.model.response.ProductResponse;
import com.microservice.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoint.Product.PREFIX)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService productService;

    @Operation(summary = "Tạo sản phẩm mới", description = "API này cho phép tạo sản phẩm mới dựa trên thông tin gửi lên dưới dạng form-data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tạo sản phẩm thành công", content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ", content = @Content)
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GlobalResponse<ProductResponse>> createProduct(
            @ModelAttribute @Valid ProductRequest request
    ) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @Operation(summary = "Lấy tất cả sản phẩm", description = "API này trả về danh sách sản phẩm có phân trang và các tùy chọn lọc.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy danh sách sản phẩm thành công", content = @Content(schema = @Schema(implementation = PageResponse.class)))
    })
    @GetMapping()
    public ResponseEntity<GlobalResponse<PageResponse<ProductResponse>>> findAllProducts(
            @RequestParam(name = "sortedBy", required = false) String sortedBy,
            @RequestParam(name = "sortDirection", required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "searchKeyword", required = false) String searchKeyword,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "minPrice", required = false) Double minPrice,
            @RequestParam(name = "maxPrice", required = false) Double maxPrice,
            @RequestParam(name = "status", required = false, defaultValue = "true") boolean status
    ) {
        return ResponseEntity.ok(productService.findAllProducts(
                sortedBy, sortDirection, page, size, searchKeyword, category, minPrice, maxPrice, status
        ));
    }

    @Operation(summary = "Lấy sản phẩm theo ID", description = "API này trả về chi tiết sản phẩm dựa trên ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy sản phẩm thành công", content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sản phẩm", content = @Content)
    })
    @GetMapping(Endpoint.Product.PRODUCT_ID)
    public ResponseEntity<GlobalResponse<ProductResponse>> getProductById(
            @PathVariable(name = "productId") UUID productId
    ) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @Operation(summary = "Tìm kiếm sản phẩm theo từ khóa", description = "API này cho phép tìm kiếm sản phẩm dựa trên từ khóa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tìm kiếm thành công", content = @Content(schema = @Schema(implementation = GlobalResponse.class)))
    })
    @GetMapping(Endpoint.Product.SEARCH)
    public ResponseEntity<GlobalResponse<List<ProductResponse>>> findByKeyword(
            @RequestParam(name = "keyword") String keyword
    ) {
        return ResponseEntity.ok(productService.searchByKeyword(keyword));
    }

    @Operation(summary = "Cập nhật sản phẩm", description = "API này cho phép cập nhật thông tin sản phẩm dựa trên ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật sản phẩm thành công", content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sản phẩm để cập nhật", content = @Content)
    })
    @PutMapping(value = Endpoint.Product.PRODUCT_ID, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GlobalResponse<ProductResponse>> updateProduct(
            @PathVariable(name = "productId") UUID productId,
            @ModelAttribute ProductRequest request
    ) {
        return ResponseEntity.ok(productService.updateProduct(productId, request));
    }

    @Operation(summary = "Tải ảnh sản phẩm", description = "API này cho phép tải ảnh sản phẩm lên dựa trên ID sản phẩm.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tải ảnh thành công", content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sản phẩm", content = @Content)
    })
    @PutMapping(Endpoint.Product.UPLOAD)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GlobalResponse<ProductResponse>> uploadImage(
            @PathVariable(name = "productId") UUID productId,
            @RequestParam("images") List<MultipartFile> images
    ) {
        return ResponseEntity.ok(productService.uploadImage(productId, images));
    }
}
