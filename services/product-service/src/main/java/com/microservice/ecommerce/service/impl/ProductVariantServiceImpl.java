package com.microservice.ecommerce.service.impl;

import com.microservice.ecommerce.exception.BusinessException;
import com.microservice.ecommerce.model.entity.Product;
import com.microservice.ecommerce.model.entity.ProductVariant;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.Status;
import com.microservice.ecommerce.model.mapper.ProductImageMapper;
import com.microservice.ecommerce.model.mapper.ProductMapper;
import com.microservice.ecommerce.model.mapper.ProductVariantMapper;
import com.microservice.ecommerce.model.request.OrderItemRequest;
import com.microservice.ecommerce.model.request.ProductVariantRequest;
import com.microservice.ecommerce.model.request.PurchaseRequest;
import com.microservice.ecommerce.model.response.ProductAttributeResponse;
import com.microservice.ecommerce.model.response.ProductPriceResponse;
import com.microservice.ecommerce.model.response.ProductResponse;
import com.microservice.ecommerce.model.response.ProductVariantResponse;
import com.microservice.ecommerce.repository.ProductRepository;
import com.microservice.ecommerce.repository.ProductVariantRepository;
import com.microservice.ecommerce.service.ProductVariantService;
import com.microservice.ecommerce.util.CloudinaryUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    06/03/2025 at 9:28 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Service
@RequiredArgsConstructor
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductVariantServiceImpl implements ProductVariantService {
    ProductVariantRepository variantRepository;
    ProductRepository productRepository;

    ProductVariantMapper variantMapper;
    ProductMapper productMapper;
    ProductImageMapper imageMapper;

    CloudinaryUtil cloudinaryUtil;

    @Override
    @Transactional
    public GlobalResponse<ProductResponse> createVariantToProduct(ProductVariantRequest variantRequest) {
        Product product = productRepository.findById(variantRequest.productId())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy sản phẩm"));

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication.getPrincipal() instanceof Jwt jwt) {
//            if (!product.getCreatedBy().equals(jwt.getClaimAsString("sub"))) {
//                throw new BusinessException("You cannot update product from another store.");
//            }
//        } else {
//            throw new BusinessException("Your authentication is not available, please try again.");
//        }

        ProductVariant variant = variantMapper.toProductVariant(variantRequest);
        variant.setProduct(product);

        if (variantRequest.image() != null && !variantRequest.image().isEmpty()) {
            try {
                String imageUrl = cloudinaryUtil.upload(variantRequest.image());

                variant.setImageUrl(imageUrl);
                log.info("Đã upload ảnh lên Cloudinary cho variant: {}", imageUrl);
            } catch (IOException e) {
                log.error("Không thể upload ảnh lên Cloudinary: {}", e.getMessage(), e);
                throw new BusinessException("Không thể upload ảnh lên Cloudinary: " + e.getMessage());
            }
        }

        variant = variantRepository.save(variant);

        variant = variantRepository.save(variant);

        product.getVariants().add(variant);

        product = productRepository.save(product);

        ProductResponse response = getProductResponse(product);

        return new GlobalResponse<>(
                Status.SUCCESS,
                response
        );
    }

    @Override
    @Transactional
    public GlobalResponse<ProductResponse> updateVariantProduct(UUID variantId, ProductVariantRequest variantRequest) {
        ProductVariant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy biến thể sản phẩm"));

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication.getPrincipal() instanceof Jwt jwt) {
//            if (!variant.getProduct().getCreatedBy().equals(jwt.getClaimAsString("sub"))) {
//                throw new BusinessException("You cannot update product from another store.");
//            }
//        } else {
//            throw new BusinessException("Your authentication is not available, please try again.");
//        }

        if (variantRequest.image() != null && !variantRequest.image().isEmpty() && variant.getImageUrl() != null && variant.getImageUrl() != null) {
            try {
                cloudinaryUtil.remove(variant.getImageUrl());
                log.info("Đã xóa ảnh cũ từ Cloudinary: {}", variant.getImageUrl());
            } catch (IOException e) {
                log.warn("Không thể xóa ảnh cũ từ Cloudinary: {}", variant.getImageUrl(), e);
                throw new BusinessException("Không thể xóa ảnh cũ từ Cloudinary: " + e.getMessage());
            }
        }

        // Upload ảnh mới lên Cloudinary nếu có
        if (variantRequest.image() != null && !variantRequest.image().isEmpty()) {
            try {
                String imageUrl = cloudinaryUtil.upload(variantRequest.image());

                variant.setImageUrl(imageUrl);
                log.info("Đã upload ảnh mới lên Cloudinary: {}", imageUrl);
            } catch (IOException e) {
                log.error("Không thể upload ảnh lên Cloudinary: {}", e.getMessage(), e);
                throw new BusinessException("Không thể upload ảnh lên Cloudinary: " + e.getMessage());
            }
        }

        variantMapper.updateProductVariant(variantRequest, variant);

        variant = variantRepository.save(variant);

        Product product = variant.getProduct();
        ProductResponse response = getProductResponse(product);

        return new GlobalResponse<>(Status.SUCCESS, response);
    }


    @Override
    public GlobalResponse<String> deleteProductVariantById(UUID variantId) {
        ProductVariant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy biến thể sản phẩm"));


//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication.getPrincipal() instanceof Jwt jwt) {
//            if (!variant.getProduct().getCreatedBy().equals(jwt.getClaimAsString("sub"))) {
//                throw new BusinessException("You cannot update product from another store.");
//            }
//        } else {
//            throw new BusinessException("You authentication is not available, please try again.");
//        }

        variantRepository.deleteById(variantId);
        return new GlobalResponse<>(
                Status.SUCCESS,
                "Xóa biến thể sản phẩm thành công"
        );
    }

    @Override
    @Transactional
    public GlobalResponse<String> uploadImageToVariant(UUID variantId, MultipartFile image) {
        ProductVariant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy biến thể sản phẩm"));

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication.getPrincipal() instanceof Jwt jwt) {
//            if (!variant.getProduct().getCreatedBy().equals(jwt.getClaimAsString("sub"))) {
//                throw new BusinessException("You cannot update product from another store.");
//            }
//        } else {
//            throw new BusinessException("Your authentication is not available, please try again.");
//        }

        // Xóa ảnh cũ từ Cloudinary nếu có
        if (variant.getImageUrl() != null) {
            try {
                cloudinaryUtil.remove(variant.getImageUrl());
                log.info("Đã xóa ảnh cũ từ Cloudinary: {}", variant.getImageUrl());
            } catch (IOException e) {
                log.warn("Không thể xóa ảnh cũ từ Cloudinary: {}", variant.getImageUrl(), e);
                throw new BusinessException("Không thể xóa ảnh cũ từ Cloudinary: " + e.getMessage());
            }
        }

        // Upload ảnh mới lên Cloudinary
        if (image != null && !image.isEmpty()) {
            try {
                String imageUrl = cloudinaryUtil.upload(image);
                variant.setImageUrl(imageUrl);
                log.info("Đã upload ảnh mới lên Cloudinary: {}", imageUrl);
            } catch (IOException e) {
                log.error("Không thể upload ảnh lên Cloudinary: {}", e.getMessage(), e);
                throw new BusinessException("Không thể upload ảnh lên Cloudinary: " + e.getMessage());
            }
        } else {
            throw new BusinessException("Không có ảnh được cung cấp để upload");
        }

        variantRepository.save(variant);

        return new GlobalResponse<>(Status.SUCCESS, "Cập nhật ảnh cho biến thể thành công");
    }

    @Override
    public Boolean checkStock(List<OrderItemRequest> requests) {
        if (requests == null || requests.isEmpty()) {
            throw new BusinessException("Danh sách sản phẩm không được để trống.");
        }

        List<UUID> variantIds = requests.stream()
                .map(OrderItemRequest::variantId)
                .collect(Collectors.toList());

        List<ProductVariant> variants = variantRepository.findAllById(variantIds);
        Map<UUID, ProductVariant> variantMap = variants.stream()
                .collect(Collectors.toMap(ProductVariant::getId, v -> v));

        for (OrderItemRequest request : requests) {
            ProductVariant variant = variantMap.get(request.variantId());

            if (variant == null) {
                throw new EntityNotFoundException("Không tìm thấy sản phẩm");
            }

            if (variant.getStock() < request.quantity()) {
                throw new BusinessException("Không đủ sản phẩm trong kho hàng");
            }
        }
        return true;
    }


    @Override
    public List<ProductPriceResponse> getPrices(PurchaseRequest request) {
        if (request.variantIds() == null || request.variantIds().isEmpty()) {
            throw new BusinessException("Danh sách biến thể không được để trống.");
        }

        List<ProductVariant> variants = variantRepository.findAllById(request.variantIds());

        return variants.stream()
                .map(variant -> new ProductPriceResponse(variant.getId(),
                        variant.getProduct().getName(),
                        request.orderedQuantities().getOrDefault(variant.getId(), 0),
                        variant.getPrice()))
                .collect(Collectors.toList());
    }


    @Override
    public Void updateStock(List<OrderItemRequest> requests) {
        if (requests == null || requests.isEmpty()) {
            throw new BusinessException("Danh sách sản phẩm không được để trống.");
        }

        List<UUID> variantIds = requests.stream()
                .map(OrderItemRequest::variantId)
                .collect(Collectors.toList());

        List<ProductVariant> variants = variantRepository.findAllById(variantIds);
        Map<UUID, ProductVariant> variantMap = variants.stream()
                .collect(Collectors.toMap(ProductVariant::getId, v -> v));

        for (OrderItemRequest request : requests) {
            ProductVariant variant = variantMap.get(request.variantId());

            if (variant == null) {
                throw new EntityNotFoundException("Không tìm thấy sản phẩm");
            }

            if (variant.getStock() < request.quantity()) {
                throw new BusinessException("Không đủ sản phẩm trong kho hàng.");
            }

            variant.setStock(variant.getStock() - request.quantity());
        }

        log.info("Cập nhật stock của các biến thể: {}", variants);
        variantRepository.saveAll(variants);
        return null;
    }

    @Override
    public GlobalResponse<ProductVariantResponse> getProductVariantById(UUID variantId) {
        ProductVariant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy biến thể sản phẩm."));

        return new GlobalResponse<>(
                Status.SUCCESS,
                ProductVariantResponse.builder()
                        .id(variantId)
                        .stock(variant.getStock())
                        .price(variant.getPrice())
                        .productName(variant.getProduct().getName())
                        .imageUrl(variant.getImageUrl())
                        .attributes(variant.getAttributes().stream().map(attribute -> ProductAttributeResponse.builder()
                                .type(attribute.getType().getValue())
                                .value(attribute.getValue())
                                .build())
                                .collect(Collectors.toList()))
                        .build()
        );
    }


    private ProductResponse getProductResponse(Product product) {
        ProductResponse response = productMapper.toProductResponse(product);
        response.setImages(product.getImages().stream()
                .map(imageMapper::toProductImageResponse
                )
                .collect(Collectors.toList())
        );
        response.setVariants(product.getVariants().stream()
                .map(productVariant -> {
                    ProductVariantResponse variantResponse = variantMapper.toProductVariantResponse(productVariant);
                    variantResponse.setAttributes(
                            productVariant.getAttributes().stream()
                                    .map(attribute -> ProductAttributeResponse.builder()
                                            .id(attribute.getId())
                                            .type(attribute.getType().getValue())
                                            .value(attribute.getValue())
                                            .build())
                                    .collect(Collectors.toList())
                    );
                    return variantResponse;
                }).toList());
        return response;
    }
}
