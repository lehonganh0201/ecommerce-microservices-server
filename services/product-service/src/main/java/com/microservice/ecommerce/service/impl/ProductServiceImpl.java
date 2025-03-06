package com.microservice.ecommerce.service.impl;

import com.microservice.ecommerce.client.UserServiceClient;
import com.microservice.ecommerce.exception.BusinessException;
import com.microservice.ecommerce.model.entity.Category;
import com.microservice.ecommerce.model.entity.Product;
import com.microservice.ecommerce.model.entity.ProductImage;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.PageResponse;
import com.microservice.ecommerce.model.global.Status;
import com.microservice.ecommerce.model.mapper.ProductImageMapper;
import com.microservice.ecommerce.model.mapper.ProductMapper;
import com.microservice.ecommerce.model.mapper.ProductVariantMapper;
import com.microservice.ecommerce.model.request.ProductRequest;
import com.microservice.ecommerce.model.response.ProductAttributeResponse;
import com.microservice.ecommerce.model.response.ProductImageResponse;
import com.microservice.ecommerce.model.response.ProductResponse;
import com.microservice.ecommerce.model.response.ProductVariantResponse;
import com.microservice.ecommerce.repository.CategoryRepository;
import com.microservice.ecommerce.repository.ProductRepository;
import com.microservice.ecommerce.service.ProductService;
import com.microservice.ecommerce.util.FileUtil;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    05/03/2025 at 10:06 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Service
@RequiredArgsConstructor
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    ProductMapper productMapper;
    ProductImageMapper imageMapper;
    ProductVariantMapper variantMapper;

    UserServiceClient userServiceClient;

    FileUtil fileUtil;

    private static final String BASE_DIRECTORY = "/resource/images/product-images/";
    private static final String ROOT_DIRECTORY = System.getProperty("user.dir");

    @Override
    public GlobalResponse<ProductResponse> createProduct(ProductRequest request) {
        try {
            var authentication = SecurityContextHolder.getContext().getAuthentication();

            String token = "Bearer ";

            if (authentication instanceof JwtAuthenticationToken authenticationToken) {
                token += authenticationToken.getToken().getTokenValue();
            }

            userServiceClient.checkUserProfile(token);
        } catch (FeignException.Unauthorized ex) {
            throw new BusinessException("User must have a profile before creating a product.");
        }

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Not found category with ID provided:: " + request.categoryId()));

        Product product = productMapper.toProduct(request);
        product.setCategory(category);
        product.setIsActive(true);

        List<ProductImage> images = new ArrayList<>();

        for (var image : request.images()) {
            if (!image.isEmpty()) {
                String filePath = fileUtil.saveFile(image, BASE_DIRECTORY);

                if (filePath == null) {
                    throw new BusinessException("Cannot upload file, please try again");
                }

                ProductImage productImage = ProductImage.builder()
                        .imageUrl(filePath)
                        .product(product)
                        .build();

                images.add(productImage);
            }
        }

        product.setImages(images);

        product = productRepository.save(product);

        List<ProductImageResponse> imageResponses = product.getImages().stream()
                .map(productImage -> ProductImageResponse.builder()
                        .id(productImage.getId())
                        .imageUrl(ROOT_DIRECTORY + productImage.getImageUrl())
                        .build())
                .collect(Collectors.toList());

        ProductResponse response = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .createdBy(product.getCreatedBy())
                .createdDate(product.getCreatedDate())
                .images(imageResponses)
                .build();

        return new GlobalResponse<>(Status.SUCCESS, response);
    }


    @Override
    public GlobalResponse<PageResponse<ProductResponse>> findAllProducts(
            String sortedBy,
            String sortDirection,
            int page,
            int size,
            String searchKeyword,
            String category,
            Double minPrice,
            Double maxPrice) {
        Sort sort = Sort.by(sortDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,
                sortedBy != null ? sortedBy : "id");

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> productPage = productRepository.findAllWithFilters(
                searchKeyword,
                category,
                minPrice,
                maxPrice,
                pageable);

        List<ProductResponse> responses = productPage.stream()
                .map(product -> {
                    var response = productMapper.toProductResponse(product);

                    List<ProductImageResponse> imageResponses = product.getImages().stream()
                            .map(imageMapper::toProductImageResponse)
                            .collect(Collectors.toList());

                    response.setImages(imageResponses);
                    return response;
                })
                .collect(Collectors.toList());

        return new GlobalResponse<>(
                Status.SUCCESS,
                new PageResponse<>(
                        responses,
                        productPage.getTotalPages(),
                        productPage.getTotalElements(),
                        productPage.getNumber(),
                        productPage.getSize(),
                        productPage.getNumberOfElements(),
                        productPage.isFirst(),
                        productPage.isLast(),
                        productPage.hasNext(),
                        productPage.hasPrevious()
                )
        );
    }

    @Override
    public GlobalResponse<ProductResponse> getProductById(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Not found product with provided:: " + productId));

        ProductResponse response = productMapper.toProductResponse(product);
        response.setImages(product.getImages().stream()
                .map(productImage -> {
                    var imageResponse = imageMapper.toProductImageResponse(productImage);
                    imageResponse.setImageUrl(ROOT_DIRECTORY + imageResponse.getImageUrl());

                    return imageResponse;
                })
                .collect(Collectors.toList())
        );
        response.setVariants(product.getVariants().stream()
                .map(variant -> {
                    ProductVariantResponse variantResponse = variantMapper.toProductVariantResponse(variant);

                    if (variant.getProduct() != null) {
                        variantResponse.setImageUrl(ROOT_DIRECTORY + variant.getImageUrl());
                    }

                    variantResponse.setAttributes(
                            variant.getAttributes().stream()
                                    .map(attribute -> {
                                        ProductAttributeResponse attributeResponse = ProductAttributeResponse.builder()
                                                .id(attribute.getId())
                                                .type(attribute.getType().getValue())
                                                .value(attribute.getValue())
                                                .build();

                                        return attributeResponse;
                                    })
                                    .collect(Collectors.toList())
                    );
                    return variantResponse;
                }).toList());

        return new GlobalResponse<>(
                Status.SUCCESS,
                response
        );
    }

    @Override
    @Transactional
    public GlobalResponse<ProductResponse> updateProduct(UUID productId, ProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof Jwt jwt) {
            if (!product.getCreatedBy().equals(jwt.getClaimAsString("name"))) {
                throw new BusinessException("You cannot update product from another store.");
            }
        } else {
            throw new BusinessException("You authentication is not available, please try again.");
        }


        productMapper.updateProduct(request, product);

        if (request.images() != null && !request.images().isEmpty()) {
            Iterator<ProductImage> iterator = product.getImages().iterator();
            while (iterator.hasNext()) {
                ProductImage oldImage = iterator.next();
                File file = new File(oldImage.getImageUrl());
                if (file.exists()) {
                    file.delete();
                }
                iterator.remove();
            }

            List<ProductImage> images = new ArrayList<>();

            for (var image : request.images()) {
                if (!image.isEmpty()) {
                    String filePath = fileUtil.saveFile(image, BASE_DIRECTORY);

                    if (filePath == null) {
                        throw new BusinessException("Cannot upload file, please try again");
                    }

                    ProductImage productImage = ProductImage.builder()
                            .imageUrl(filePath)
                            .product(product)
                            .build();

                    images.add(productImage);
                }
            }

            product.setImages(images);
        }

        product = productRepository.save(product);

        List<ProductImageResponse> imageResponses = product.getImages().stream()
                .map(productImage -> ProductImageResponse.builder()
                        .id(productImage.getId())
                        .imageUrl(ROOT_DIRECTORY + productImage.getImageUrl())
                        .build())
                .collect(Collectors.toList());

        ProductResponse response = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .createdBy(product.getCreatedBy())
                .createdDate(product.getCreatedDate())
                .images(imageResponses)
                .build();

        return new GlobalResponse<>(Status.SUCCESS, response);
    }

    @Override
    @Transactional
    public GlobalResponse<ProductResponse> uploadImage(UUID productId, List<MultipartFile> images) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof Jwt jwt) {
            if (!product.getCreatedBy().equals(jwt.getClaimAsString("name"))) {
                throw new BusinessException("You cannot update product from another store.");
            }
        } else {
            throw new BusinessException("You authentication is not available, please try again.");
        }

        List<ProductImage> newImages = new ArrayList<>();

            for (var image : images) {
                if (!image.isEmpty()) {
                    String filePath = fileUtil.saveFile(image, BASE_DIRECTORY);

                    if (filePath == null) {
                        throw new BusinessException("Cannot upload file, please try again");
                    }

                    ProductImage productImage = ProductImage.builder()
                            .imageUrl(filePath)
                            .product(product)
                            .build();

                    newImages.add(productImage);
                }
            }

            product.getImages().addAll(newImages);
            productRepository.save(product);

            List<ProductImageResponse> imageResponses = product.getImages().stream()
                    .map(productImage -> ProductImageResponse.builder()
                            .id(productImage.getId())
                            .imageUrl(ROOT_DIRECTORY + productImage.getImageUrl())
                            .build())
                    .collect(Collectors.toList());

            ProductResponse response = ProductResponse.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .stock(product.getStock())
                    .createdBy(product.getCreatedBy())
                    .createdDate(product.getCreatedDate())
                    .images(imageResponses)
                    .build();

            return new GlobalResponse<>(Status.SUCCESS, response);
    }
}
