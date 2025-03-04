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
@Table(name = "categories")
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, unique = true)
    UUID id;

    @Column(nullable = false, unique = true)
    String name;

    String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Product> products = new ArrayList<>();
}
