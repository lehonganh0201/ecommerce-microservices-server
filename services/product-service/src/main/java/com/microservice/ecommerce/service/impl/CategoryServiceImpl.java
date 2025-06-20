package com.microservice.ecommerce.service.impl;

import com.microservice.ecommerce.exception.BusinessException;
import com.microservice.ecommerce.model.entity.Category;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.PageResponse;
import com.microservice.ecommerce.model.global.Status;
import com.microservice.ecommerce.model.mapper.CategoryMapper;
import com.microservice.ecommerce.model.request.CategoryRequest;
import com.microservice.ecommerce.model.response.CategoryResponse;
import com.microservice.ecommerce.repository.CategoryRepository;
import com.microservice.ecommerce.service.CategoryService;
import com.microservice.ecommerce.util.CloudinaryUtil;
import com.microservice.ecommerce.util.FileUtil;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    06/03/2025 at 6:05 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Service
@RequiredArgsConstructor
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;

    CategoryMapper categoryMapper;

    CloudinaryUtil cloudinaryUtil;

    @Override
    public GlobalResponse<CategoryResponse> createCategory(CategoryRequest request) {
        Category category = categoryMapper.toCategory(request);

        if (request.image() != null && !request.image().isEmpty()) {
            try {
                String imageUrl = cloudinaryUtil.upload(request.image());
                category.setImageUrl(imageUrl);
            } catch (IOException e) {
                throw new BusinessException("Cannot upload image to Cloudinary: " + e.getMessage());
            }
        }

        category = categoryRepository.save(category);

        return new GlobalResponse<>(
                Status.SUCCESS,
                new CategoryResponse(
                        category.getId(),
                        category.getName(),
                        category.getDescription(),
                        category.getImageUrl()
                )
        );
    }

    @Override
    public GlobalResponse<PageResponse<CategoryResponse>> findAllCategories(
            String sortedBy, String sortDirection,
            int page, int size,
            String searchKeyword) {
        Sort sort = Sort.by(sortDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,
                sortedBy != null ? sortedBy : "id");

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Category> categoryPage = categoryRepository.findAllWithFilters(searchKeyword, pageable);

        List<CategoryResponse> responses = categoryPage.stream()
                .map(category -> new CategoryResponse(
                        category.getId(),
                        category.getName(),
                        category.getDescription(),
                        category.getImageUrl()
                ))
                .collect(Collectors.toList());

        return new GlobalResponse<>(
                Status.SUCCESS,
                new PageResponse<>(
                        responses,
                        categoryPage.getTotalPages(),
                        categoryPage.getTotalElements(),
                        categoryPage.getNumber(),
                        categoryPage.getSize(),
                        categoryPage.getNumberOfElements(),
                        categoryPage.isFirst(),
                        categoryPage.isLast(),
                        categoryPage.hasNext(),
                        categoryPage.hasPrevious()
                )
        );
    }

    @Override
    @Transactional
    public GlobalResponse<CategoryResponse> updateCategory(UUID categoryId, CategoryRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Not found category with the ID provided"));

        categoryMapper.updateCategory(request, category);

        if (request.image() != null && !request.image().isEmpty()) {
            if (category.getImageUrl() != null) {
                try {
                    cloudinaryUtil.remove(category.getImageUrl());
                } catch (IOException e) {
                    throw new BusinessException("Cannot delete old image from Cloudinary: " + e.getMessage());
                }
            }

            try {
                String imageUrl = cloudinaryUtil.upload(request.image());
                category.setImageUrl(imageUrl);
            } catch (IOException e) {
                throw new BusinessException("Cannot upload image to Cloudinary: " + e.getMessage());
            }
        }

        category = categoryRepository.save(category);

        return new GlobalResponse<>(
                Status.SUCCESS,
                new CategoryResponse(
                        category.getId(),
                        category.getName(),
                        category.getDescription(),
                        category.getImageUrl()
                )
        );
    }

    @Override
    public GlobalResponse<String> deleteCategory(UUID categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Danh mục không tồn tại");
        }
        categoryRepository.deleteById(categoryId);

        return new GlobalResponse<>(
          Status.SUCCESS,
          "Xóa danh mục thành công"
        );
    }

    @Override
    @Transactional
    public GlobalResponse<CategoryResponse> uploadImage(UUID categoryId, MultipartFile image) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy danh mục với ID cung cấp"));

        if (category.getImageUrl() != null) {
            try {
                cloudinaryUtil.remove(category.getImageUrl());
                log.info("Đã xóa ảnh cũ từ Cloudinary: {}", category.getImageUrl());
            } catch (IOException e) {
                log.warn("Không thể xóa ảnh cũ từ Cloudinary: {}", category.getImageUrl(), e);
                throw new BusinessException("Không thể xóa ảnh cũ từ Cloudinary: " + e.getMessage());
            }
        }

        if (image != null && !image.isEmpty()) {
            try {
                String imageUrl = cloudinaryUtil.upload(image);
                category.setImageUrl(imageUrl);
                log.info("Đã upload ảnh mới lên Cloudinary: {}", imageUrl);
            } catch (IOException e) {
                log.error("Không thể upload ảnh lên Cloudinary: {}", e.getMessage(), e);
                throw new BusinessException("Không thể upload ảnh lên Cloudinary: " + e.getMessage());
            }
        } else {
            throw new BusinessException("Không có ảnh được cung cấp để upload");
        }

        categoryRepository.save(category);

        return new GlobalResponse<>(
                Status.SUCCESS,
                new CategoryResponse(
                        category.getId(),
                        category.getName(),
                        category.getDescription(),
                        category.getImageUrl()
                )
        );
    }

    @Override
    public GlobalResponse<CategoryResponse> findCategoryById(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy danh mục với ID cung cấp"));

        return new GlobalResponse<>(
                Status.SUCCESS,
                new CategoryResponse(
                        category.getId(),
                        category.getName(),
                        category.getDescription(),
                        category.getImageUrl()
                )
        );
    }
}
