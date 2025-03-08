package com.microservice.ecommerce.repository;

import com.microservice.ecommerce.model.document.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    08/03/2025 at 4:22 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Repository
public interface ProductDocumentRepository extends ElasticsearchRepository<ProductDocument, UUID> {
}
