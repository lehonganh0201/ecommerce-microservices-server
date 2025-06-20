package com.microservice.ecommerce.service.impl;

import com.microservice.ecommerce.client.UserServiceClient;
import com.microservice.ecommerce.exception.BusinessException;
import com.microservice.ecommerce.model.document.ProductDocument;
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
import com.microservice.ecommerce.model.request.UpdateProductRequest;
import com.microservice.ecommerce.model.response.ProductAttributeResponse;
import com.microservice.ecommerce.model.response.ProductImageResponse;
import com.microservice.ecommerce.model.response.ProductResponse;
import com.microservice.ecommerce.model.response.ProductVariantResponse;
import com.microservice.ecommerce.repository.CategoryRepository;
import com.microservice.ecommerce.repository.ProductDocumentRepository;
import com.microservice.ecommerce.repository.ProductRepository;
import com.microservice.ecommerce.service.ProductService;
import com.microservice.ecommerce.util.CloudinaryUtil;
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
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    ProductDocumentRepository productDocumentRepository;

    ProductMapper productMapper;
    ProductImageMapper imageMapper;
    ProductVariantMapper variantMapper;

    UserServiceClient userServiceClient;

    FileUtil fileUtil;
    CloudinaryUtil cloudinaryUtil;

    ElasticsearchOperations elasticsearchOperations;

    @Override
    public GlobalResponse<ProductResponse> createProduct(ProductRequest request) {
        String creatorName = null;
        try {
            var authentication = SecurityContextHolder.getContext().getAuthentication();

            String token = "Bearer ";

            if (authentication instanceof JwtAuthenticationToken authenticationToken) {
                token += authenticationToken.getToken().getTokenValue();
                creatorName = (String) ((JwtAuthenticationToken) authentication).getTokenAttributes().get("name");
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
        product.setCreatorName(creatorName);

        List<ProductImage> images = Collections.synchronizedList(new ArrayList<>());
        ExecutorService executorService = Executors.newFixedThreadPool(Math.min(request.images().size(), 4)); // Giới hạn tối đa 4 thread

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (var image : request.images()) {
            if (!image.isEmpty()) {
                Product finalProduct = product;
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    try {
                        String imageUrl = cloudinaryUtil.upload(image);
                        ProductImage productImage = ProductImage.builder()
                                .imageUrl(imageUrl)
                                .product(finalProduct)
                                .build();
                        images.add(productImage);
                    } catch (IOException e) {
                        throw new RuntimeException("Cannot upload image to Cloudinary: " + e.getMessage(), e);
                    }
                }, executorService);
                futures.add(future);
            }
        }

        try {
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        } catch (Exception e) {
            throw new BusinessException("Failed to upload one or more images: " + e.getMessage());
        } finally {
            executorService.shutdown();
        }

        product.setImages(images);

        product = productRepository.save(product);

        syncProduct(product);

        List<ProductImageResponse> imageResponses = product.getImages().stream()
                .map(productImage -> ProductImageResponse.builder()
                        .id(productImage.getId())
                        .imageUrl(productImage.getImageUrl())
                        .build())
                .collect(Collectors.toList());

        ProductResponse response = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .creatorName(product.getCreatorName())
                .createdDate(product.getCreatedDate())
                .images(imageResponses)
                .isActive(product.getIsActive())
                .categoryId(category.getId())
                .categoryName(category.getName())
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
            Double maxPrice,
            boolean status) {
        Sort sort = Sort.by(sortDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,
                sortedBy != null ? sortedBy : "id");

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> productPage = productRepository.findAllWithFilters(
                searchKeyword,
                category,
                minPrice,
                maxPrice,
                status,
                pageable);

        List<ProductResponse> responses = productPage.stream()
                .map(product -> {
                    var response = productMapper.toProductResponse(product);

                    List<ProductImageResponse> imageResponses = product.getImages().stream()
                            .map(productImage -> {
                                ProductImageResponse imageResponse = imageMapper.toProductImageResponse(productImage);
//                                imageResponse.setImageUrl(ROOT_DIRECTORY + imageResponse.getImageUrl());

                                return imageResponse;
                            })
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

        Category category = categoryRepository.findByProductId(productId);

        ProductResponse response = productMapper.toProductResponse(product);
        response.setImages(product.getImages().stream()
                .map(productImage -> {
                    var imageResponse = imageMapper.toProductImageResponse(productImage);

                    if (imageResponse.getImageUrl() != null) {
                        imageResponse.setImageUrl(imageResponse.getImageUrl());
                    }

                    return imageResponse;
                })
                .collect(Collectors.toList())
        );
        response.setVariants(product.getVariants().stream()
                .map(variant -> {
                    ProductVariantResponse variantResponse = variantMapper.toProductVariantResponse(variant);

                    if (variant.getImageUrl() != null) {
                        variantResponse.setImageUrl(variant.getImageUrl());
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

        log.info("CATEGORY: {}", category);
        response.setCategoryId(category.getId());
        response.setCategoryName(category.getName());
        response.setIsActive(product.getIsActive());

        return new GlobalResponse<>(
                Status.SUCCESS,
                response
        );
    }

    @Override
    @Transactional
    public GlobalResponse<ProductResponse> updateProduct(UUID productId, UpdateProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId));

        productMapper.updateProduct(request, product);

        if (request.images() != null || request.existingImageUrls() != null) {
            List<ProductImage> currentImages = product.getImages();

            List<String> currentImageUrls = currentImages.stream()
                    .map(ProductImage::getImageUrl)
                    .collect(Collectors.toList());

            List<String> existingImageUrls = request.existingImageUrls() != null ? request.existingImageUrls() : new ArrayList<>();

            List<ProductImage> imagesToDelete = currentImages.stream()
                    .filter(image -> !existingImageUrls.contains(image.getImageUrl()))
                    .collect(Collectors.toList());

            Iterator<ProductImage> iterator = product.getImages().iterator();
            while (iterator.hasNext()) {
                ProductImage oldImage = iterator.next();
                if (imagesToDelete.contains(oldImage)) {
                    try {
                        cloudinaryUtil.remove(oldImage.getImageUrl());
                        iterator.remove();
                    } catch (IOException e) {
                        throw new BusinessException("Cannot delete old image from Cloudinary: " + e.getMessage());
                    }
                }
            }

            List<ProductImage> newImages = Collections.synchronizedList(new ArrayList<>());
            if (request.images() != null && !request.images().isEmpty()) {
                ExecutorService executorService = Executors.newFixedThreadPool(Math.min(request.images().size(), 4));
                List<CompletableFuture<Void>> futures = new ArrayList<>();

                for (var image : request.images()) {
                    if (!image.isEmpty()) {
                        Product finalProduct = product;
                        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                            try {
                                String imageUrl = cloudinaryUtil.upload(image);
                                ProductImage productImage = ProductImage.builder()
                                        .imageUrl(imageUrl)
                                        .product(finalProduct)
                                        .build();
                                newImages.add(productImage);
                            } catch (IOException e) {
                                throw new RuntimeException("Cannot upload image to Cloudinary: " + e.getMessage(), e);
                            }
                        }, executorService);
                        futures.add(future);
                    }
                }

                try {
                    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
                } catch (Exception e) {
                    throw new BusinessException("Failed to upload one or more images: " + e.getMessage());
                } finally {
                    executorService.shutdown();
                }
            }

            product.getImages().addAll(newImages);
        }

        if (request.categoryId() != null) {
            Category category = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + request.categoryId()));
            product.setCategory(category);
        }

        product = productRepository.save(product);

        List<ProductImageResponse> imageResponses = product.getImages().stream()
                .map(productImage -> ProductImageResponse.builder()
                        .id(productImage.getId())
                        .imageUrl(productImage.getImageUrl())
                        .build())
                .collect(Collectors.toList());

        Category category = categoryRepository.findByProductId(productId);

        ProductResponse response = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .creatorName(product.getCreatorName())
                .createdDate(product.getCreatedDate())
                .isActive(product.getIsActive())
                .images(imageResponses)
                .categoryId(category.getId())
                .categoryName(category.getName())
                .build();

        return new GlobalResponse<>(Status.SUCCESS, response);
    }

    @Override
    @Transactional
    public GlobalResponse<ProductResponse> uploadImage(UUID productId, List<MultipartFile> images) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId));

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication.getPrincipal() instanceof Jwt jwt) {
//            if (!product.getCreatedBy().equals(jwt.getClaimAsString("sub"))) {
//                throw new BusinessException("You cannot update product from another store.");
//            }
//        } else {
//            throw new BusinessException("Your authentication is not available, please try again.");
//        }

        List<ProductImage> newImages = Collections.synchronizedList(new ArrayList<>());
        ExecutorService executorService = Executors.newFixedThreadPool(Math.min(images.size(), 4)); // Giới hạn tối đa 4 thread
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (var image : images) {
            if (!image.isEmpty()) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    try {
                        String imageUrl = cloudinaryUtil.upload(image);
                        ProductImage productImage = ProductImage.builder()
                                .imageUrl(imageUrl)
                                .product(product)
                                .build();
                        newImages.add(productImage);
                    } catch (IOException e) {
                        throw new RuntimeException("Cannot upload image to Cloudinary: " + e.getMessage(), e);
                    }
                }, executorService);
                futures.add(future);
            }
        }

        // Chờ tất cả các tác vụ upload hoàn thành
        try {
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        } catch (Exception e) {
            throw new BusinessException("Failed to upload one or more images: " + e.getMessage());
        } finally {
            executorService.shutdown();
        }

        product.getImages().addAll(newImages);
        productRepository.save(product);

        List<ProductImageResponse> imageResponses = product.getImages().stream()
                .map(productImage -> ProductImageResponse.builder()
                        .id(productImage.getId())
                        .imageUrl(productImage.getImageUrl()) // Cloudinary URL không cần ROOT_DIRECTORY
                        .build())
                .collect(Collectors.toList());

        Category category = categoryRepository.findByProductId(productId);

        ProductResponse response = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .creatorName(product.getCreatorName())
                .createdDate(product.getCreatedDate())
                .images(imageResponses)
                .isActive(product.getIsActive())
                .categoryId(category.getId())
                .categoryName(category.getName())
                .build();

        return new GlobalResponse<>(Status.SUCCESS, response);
    }

    @Override
    public GlobalResponse<List<ProductResponse>> searchByKeyword(String keyword) {
        Criteria criteria = new Criteria("name").fuzzy(keyword)
                .or(new Criteria("description").fuzzy(keyword));

        CriteriaQuery searchQuery = new CriteriaQuery(criteria);

        List<ProductDocument> products = elasticsearchOperations.search(searchQuery, ProductDocument.class)
                .stream()
                .map(SearchHit::getContent)
                .toList();

        List<ProductResponse> productResponses = products.stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getStock(),
                        null,
                        null,
                        product.getIsActive(),
                        null,
                        null,
                        null,
                        null))
                .toList();

        return new GlobalResponse<>(Status.SUCCESS, productResponses);
    }

    @Override
    public GlobalResponse<ProductResponse> changeStatusForProduct(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId));

        product.setIsActive(!product.getIsActive());

        product = productRepository.save(product);

        List<ProductImageResponse> imageResponses = product.getImages().stream()
                .map(productImage -> ProductImageResponse.builder()
                        .id(productImage.getId())
                        .imageUrl(productImage.getImageUrl())
                        .build())
                .collect(Collectors.toList());

        Category category = categoryRepository.findByProductId(productId);

        ProductResponse response = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .creatorName(product.getCreatorName())
                .createdDate(product.getCreatedDate())
                .isActive(product.getIsActive())
                .images(imageResponses)
                .categoryId(category.getId())
                .categoryName(category.getName())
                .build();

        return new GlobalResponse<>(
                Status.SUCCESS,
                response
        );
    }


    private void syncProduct(Product product) {
        ProductDocument document = ProductDocument.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .creatorName(product.getCreatorName())
                .isActive(product.getIsActive())
                .build();

        productDocumentRepository.save(document);
    }
}
