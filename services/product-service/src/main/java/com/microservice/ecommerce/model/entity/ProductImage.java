package com.microservice.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    04/03/2025 at 12:11 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_images")
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    String imageUrl; // Đường dẫn ảnh sản phẩm

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    Product product;
}
