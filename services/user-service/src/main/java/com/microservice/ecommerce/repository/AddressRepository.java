package com.microservice.ecommerce.repository;

import com.microservice.ecommerce.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 8:22 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
