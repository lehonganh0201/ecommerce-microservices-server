package com.microservice.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    04/03/2025 at 12:10 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "variants")
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    Integer stock;

    Double price;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProductAttribute> attributes = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "image_id")
    ProductImage image;
}
