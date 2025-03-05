package com.microservice.ecommerce.service.impl;

import com.microservice.ecommerce.model.entity.Category;
import com.microservice.ecommerce.model.entity.Product;
import com.microservice.ecommerce.model.entity.ProductImage;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.PageResponse;
import com.microservice.ecommerce.model.global.Status;
import com.microservice.ecommerce.model.mapper.ProductImageMapper;
import com.microservice.ecommerce.model.mapper.ProductMapper;
import com.microservice.ecommerce.model.request.ProductRequest;
import com.microservice.ecommerce.model.response.ProductImageResponse;
import com.microservice.ecommerce.model.response.ProductResponse;
import com.microservice.ecommerce.repository.CategoryRepository;
import com.microservice.ecommerce.repository.ProductImageRepository;
import com.microservice.ecommerce.repository.ProductRepository;
import com.microservice.ecommerce.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    ProductImageRepository imageRepository;
    CategoryRepository categoryRepository;

    ProductMapper productMapper;
    ProductImageMapper imageMapper;

    private static final String BASE_DIRECTORY = "./resource/images/product-images/";
    private static final String ROOT_DIRECTORY = System.getProperty("user.dir");

    @Override
    public GlobalResponse<ProductResponse> createProduct(ProductRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Not found category with ID provided:: " + request.categoryId()));

        Product product = productMapper.toProduct(request);
        product.setCategory(category);

        List<ProductImage> images = new ArrayList<>();

        try {
            File directory = new File(BASE_DIRECTORY);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            for (var image : request.images()) {
                if (!image.isEmpty()) {
                    String originalFilename = StringUtils.cleanPath(image.getOriginalFilename());
                    String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                    String newFileName = UUID.randomUUID() + fileExtension;
                    Path filePath = Paths.get(BASE_DIRECTORY + newFileName);

                    Files.write(filePath, image.getBytes());

                    ProductImage productImage = ProductImage.builder()
                            .imageUrl(filePath.toString())
                            .product(product) // 🔹 Gán product vào từng ProductImage
                            .build();

                    images.add(productImage);
                }
            }

            product.setImages(images); // 🔹 Gán danh sách ảnh vào Product trước khi lưu

            product = productRepository.save(product); // 🔹 Chỉ gọi save một lần để tránh mất quan hệ

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
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException("Không thể tạo sản phẩm, vui lòng thử lại sau.");
        }
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
}
