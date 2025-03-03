package com.microservice.ecommerce.service.impl;

import com.microservice.ecommerce.constant.RoleType;
import com.microservice.ecommerce.mapper.AddressMapper;
import com.microservice.ecommerce.mapper.UserMapper;
import com.microservice.ecommerce.model.entity.Address;
import com.microservice.ecommerce.model.entity.Role;
import com.microservice.ecommerce.model.entity.User;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.Status;
import com.microservice.ecommerce.model.request.UserRequest;
import com.microservice.ecommerce.model.response.UserResponse;
import com.microservice.ecommerce.repository.AddressRepository;
import com.microservice.ecommerce.repository.RoleRepository;
import com.microservice.ecommerce.repository.UserRepository;
import com.microservice.ecommerce.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 8:03 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Service
@RequiredArgsConstructor
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    AddressRepository addressRepository;
    RoleRepository roleRepository;

    UserMapper userMapper;
    AddressMapper addressMapper;

    @Override
    public GlobalResponse<UserResponse> createUser(UserRequest request, Jwt jwt) {
        User user = userMapper.toUser(request);
        Address address = addressMapper.toAddress(request.address());

        user.setId(jwt.getSubject());
        user.setUsername((String) jwt.getClaims().get("preferred_username"));
        user.setEmail((String) jwt.getClaims().get("email"));

        address.setUser(user);

        Role role = roleRepository.findByName(RoleType.CUSTOMER.getName())
                .orElseGet(() -> roleRepository.save(Role.builder()
                        .name(RoleType.CUSTOMER.getName())
                        .description("This role for customer")
                        .build()));

        user.setRole(role);

        user = userRepository.save(user);
        address = addressRepository.save(address);

        UserResponse response = new UserResponse(
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                addressMapper.toAddressResponse(address)
        );

        return new GlobalResponse<>(
                Status.SUCCESS,
                response
        );
    }
}
