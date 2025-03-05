package com.microservice.ecommerce.config;

import com.microservice.ecommerce.job.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    05/03/2025 at 7:38 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Configuration
public class JpaAuditingConfig {

    @Bean
    public AuditorAware<String> auditorAwareProvider() {
        return new AuditorAwareImpl();
    }
}
