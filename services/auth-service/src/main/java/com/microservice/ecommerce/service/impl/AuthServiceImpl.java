package com.microservice.ecommerce.service.impl;

import com.microservice.ecommerce.config.KeycloakProperties;
import com.microservice.ecommerce.exception.AuthenticationException;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.Status;
import com.microservice.ecommerce.model.request.AuthRequest;
import com.microservice.ecommerce.model.request.LoginRequest;
import com.microservice.ecommerce.model.response.TokenResponse;
import com.microservice.ecommerce.service.AuthService;
import com.microservice.ecommerce.util.EmailUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.APPLICATION_JSON;


/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 6:54 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Service
@RequiredArgsConstructor
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {
    RestTemplate restTemplate;
    KeycloakProperties keycloakProperties;

    EmailUtil emailUtil;

    @NonFinal
    @Value("${application.url.callback}")
    String callbackUrl;

    @NonFinal
    @Value("${application.url.returnUrl}")
    String fontEndUrl;

    final String BEARER_PREFIX = "Bearer ";


    @Override
    public GlobalResponse<String> register(AuthRequest request) {
        final String url = keycloakProperties.serverUrl() + "/admin/realms/" + keycloakProperties.realm() + "/users";

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, BEARER_PREFIX + getAdminToken());
        headers.setContentType(APPLICATION_JSON);

        Map<String, Object> user = new HashMap<>();
        user.put("username", request.username());
        user.put("enabled", true);
        user.put("firstName", request.firstName());
        user.put("lastName", request.lastName());
        user.put("email", request.email());
        user.put("emailVerified", false);
        user.put("credentials", List.of(Map.of(
                "type", "password",
                "value", request.password(),
                "temporary", false
        )));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, POST, entity, String.class);

        if (response.getStatusCode().isError()) {
            return new GlobalResponse<>(
                    Status.ERROR,
                    "Cannot register, please try again"
            );
        }

        String userId = getUserId(request.username());

        String roleId = null;
        try {
            roleId = getRoleId("USER");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            assignRoleToUser(userId, roleId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        emailUtil.sendVerificationEmail(userId);

        return new GlobalResponse<>(
                Status.SUCCESS,
                "Create user successfully!"
        );
    }

    private void assignRoleToUser(String userId, String roleId) throws JSONException {
        String url = keycloakProperties.serverUrl() + "/admin/realms/" + keycloakProperties.realm() + "/users/" + userId + "/role-mappings/realm";

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, BEARER_PREFIX + getAdminToken());
        headers.setContentType(APPLICATION_JSON);

        JSONArray roleArray = new JSONArray();
        JSONObject roleObject = new JSONObject();
        roleObject.put("id", roleId);
        roleObject.put("name", "USER");
        roleArray.put(roleObject);

        HttpEntity<String> entity = new HttpEntity<>(roleArray.toString(), headers);
        restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }


    private String getRoleId(String roleName) throws JSONException {
        String url = keycloakProperties.serverUrl() + "/admin/realms/" + keycloakProperties.realm() + "/roles/" + roleName;

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, BEARER_PREFIX + getAdminToken());

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        JSONObject role = new JSONObject(response.getBody());
        return role.getString("id");
    }


    @Override
    public GlobalResponse<TokenResponse> login(LoginRequest request) {
        final String url = keycloakProperties.serverUrl() + "/realms/" + keycloakProperties.realm() + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", keycloakProperties.clientId());
        params.add("client_secret", keycloakProperties.clientSecret());
        params.add("grant_type", "password");
        params.add("username", request.username());
        params.add("password", request.password());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> body = response.getBody();

            return new GlobalResponse<>(
                    Status.SUCCESS,
                    new TokenResponse(
                            (String) body.get("access_token"),
                            (String) body.get("refresh_token"),
                            ((Number) body.get("expires_in")).longValue(),
                            null
                    )
            );
        }else {
            throw new AuthenticationException("Please try again, login fails");
        }
    }

    @Override
    public GlobalResponse<String> loginWithGoogle(HttpServletResponse response) {

        String encodedRedirectUri = URLEncoder.encode(callbackUrl, StandardCharsets.UTF_8);

        String keycloakLoginUrl = keycloakProperties.serverUrl() + "/realms/" + keycloakProperties.realm() + "/protocol/openid-connect/auth"
                + "?client_id=" + keycloakProperties.clientId()
                + "&redirect_uri=" + encodedRedirectUri
                + "&response_type=code"
                + "&scope=openid%20profile%20email"
                + "&kc_idp_hint=google";

        return new GlobalResponse<>(
                Status.SUCCESS,
                keycloakLoginUrl
        );
    }

    @Override
    public GlobalResponse<TokenResponse> handleGoogleCallback(String code) {
        final String url = keycloakProperties.serverUrl() + "/realms/" + keycloakProperties.realm() + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", keycloakProperties.clientId());
        params.add("client_secret", keycloakProperties.clientSecret());
        params.add("grant_type", "authorization_code");
        params.add("code", code);
        params.add("redirect_uri", callbackUrl);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> body = response.getBody();

            return new GlobalResponse<>(
                    Status.SUCCESS,
                    new TokenResponse(
                            (String) body.get("access_token"),
                            (String) body.get("refresh_token"),
                            ((Number) body.get("expires_in")).longValue(),
                            fontEndUrl
                    )
            );
        } else {
            throw new AuthenticationException("Login with Google failed, please try again.");
        }
    }

    @Override
    public GlobalResponse<String> forgotPassword(String username) {
        String userId = getUserId(username);

        emailUtil.sendResetPasswordEmail(userId);

        return new GlobalResponse<>(Status.SUCCESS, "Password reset email has been sent. Please check your email.");
    }

    @Override
    public GlobalResponse<String> changePassword(String username, String currentPassword, String newPassword) {
        try {
            String tokenUrl = keycloakProperties.serverUrl() + "/realms/" + keycloakProperties.realm() + "/protocol/openid-connect/token";

            HttpHeaders tokenHeaders = new HttpHeaders();
            tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> tokenParams = new LinkedMultiValueMap<>();
            tokenParams.add("client_id", keycloakProperties.clientId());
            tokenParams.add("client_secret", keycloakProperties.clientSecret());
            tokenParams.add("grant_type", "password");
            tokenParams.add("username", username);
            tokenParams.add("password", currentPassword);

            HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(tokenParams, tokenHeaders);
            ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenUrl, tokenRequest, Map.class);

            if (!tokenResponse.getStatusCode().is2xxSuccessful()) {
                return new GlobalResponse<>(Status.ERROR, "Sai mật khẩu hiện tại hoặc tài khoản không tồn tại.");
            }

            String accessToken = (String) tokenResponse.getBody().get("access_token");

            String userId = getUserId(username);
            String changePasswordUrl = keycloakProperties.serverUrl() + "/admin/realms/" + keycloakProperties.realm() + "/users/" + userId + "/reset-password";

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(getAdminToken());
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> credentials = new HashMap<>();
            credentials.put("type", "password");
            credentials.put("value", newPassword);
            credentials.put("temporary", false);

            HttpEntity<Map<String, Object>> changePasswordRequest = new HttpEntity<>(credentials, headers);

            restTemplate.put(changePasswordUrl, changePasswordRequest);

            return new GlobalResponse<>(Status.SUCCESS, "Đổi mật khẩu thành công!");

        } catch (Exception e) {
            log.error("Lỗi khi đổi mật khẩu cho user {}: {}", username, e.getMessage());
            return new GlobalResponse<>(Status.ERROR, "Đổi mật khẩu thất bại. Vui lòng thử lại sau.");
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

    private String getUserId(String username) {
        final String url = keycloakProperties.serverUrl() + "/admin/realms/" + keycloakProperties.realm() + "/users?username=" + username;

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, BEARER_PREFIX + getAdminToken());

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && !response.getBody().isEmpty()) {
            Map<String, Object> user = (Map<String, Object>) response.getBody().get(0);
            return (String) user.get("id");
        }
        throw new RuntimeException("User not found in Keycloak");
    }
}
