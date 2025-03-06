package com.microservice.ecommerce.util;

import com.microservice.ecommerce.config.KeycloakProperties;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    06/03/2025 at 7:30 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Component
@RequiredArgsConstructor
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailUtil {
    RestTemplate restTemplate;
    KeycloakProperties keycloakProperties;

    final String BEARER_PREFIX = "Bearer ";

    public void sendResetPasswordEmail(String userId) {
        final String url = keycloakProperties.serverUrl() + "/admin/realms/" + keycloakProperties.realm() + "/users/" + userId + "/execute-actions-email";

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, BEARER_PREFIX + getAdminToken());
        headers.setContentType(APPLICATION_JSON);

        HttpEntity<List<String>> entity = new HttpEntity<>(List.of("UPDATE_PASSWORD"), headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Reset password email sent successfully to userId: {}", userId);
        } else {
            log.error("Failed to send reset password email to userId: {}", userId);
        }
    }

    public void sendVerificationEmail(String userId) {
        final String url = keycloakProperties.serverUrl() + "/admin/realms/" + keycloakProperties.realm() + "/users/" + userId + "/send-verify-email";

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, BEARER_PREFIX + getAdminToken());
        headers.setContentType(APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Verification email sent successfully to userId: {}", userId);
        } else {
            log.error("Failed to send verification email to userId: {}", userId);
        }
    }

    private String getAdminToken() {
        final String adminUrl = keycloakProperties.serverUrl() + "/realms/master/protocol/openid-connect/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", "admin-cli");
        params.add("grant_type", "password");
        params.add("username", keycloakProperties.adminUser());
        params.add("password", keycloakProperties.adminPassword());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(adminUrl, entity, Map.class);

        return (String) response.getBody().get("access_token");
    }

}
