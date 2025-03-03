package com.microservice.ecommerce.repository;

import com.microservice.ecommerce.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 8:37 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    boolean existsByName(String name);
    Optional<Role> findByName(String name);
}
