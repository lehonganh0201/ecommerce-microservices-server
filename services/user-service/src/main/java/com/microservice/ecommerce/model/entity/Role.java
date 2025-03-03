package com.microservice.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 7:49 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Builder
@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @Column(nullable = false, updatable = false, unique = true)
    String name;

    String description;
}
