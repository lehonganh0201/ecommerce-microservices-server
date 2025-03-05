package com.microservice.ecommerce.service.impl;

import com.microservice.ecommerce.constant.RoleType;
import com.microservice.ecommerce.exception.BusinessException;
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
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

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
                (String) jwt.getClaims().get("name"),
                user.getEmail(),
                user.getPhoneNumber(),
                List.of(addressMapper.toAddressResponse(address))
        );

        return new GlobalResponse<>(
                Status.SUCCESS,
                response
        );
    }

    @Override
    public GlobalResponse<UserResponse> findCurrentUser(Jwt jwt) {
        var user = userRepository.findById(jwt.getSubject());

        if (user.isPresent()) {
            UserResponse response = new UserResponse(
                    user.get().getUsername(),
                    (String) jwt.getClaims().get("name"),
                    user.get().getEmail(),
                    user.get().getPhoneNumber(),
                    null
            );

            return new GlobalResponse<>(
                    Status.SUCCESS,
                    response
            );
        }

        return new GlobalResponse<>(
                Status.ERROR,
                null
        );
    }

    @Override
    public GlobalResponse<UserResponse> updateUser(Integer addressId, UserRequest request, Jwt jwt) {
        User user = userRepository.findById(jwt.getSubject())
                .orElseThrow(() -> new EntityNotFoundException("Not found current user, please try again"));
        Address address = null;
        if (addressId != null) {
             address = addressRepository.findById(addressId)
                    .orElseThrow(() -> new EntityNotFoundException("Not found address id provide " + addressId));

            if (!address.getUser().equals(user)) {
                throw new BusinessException("This address does not belong to the current user");
            }

            addressMapper.updateAddress(request.address(), address);

            address = addressRepository.save(address);
        }

        userMapper.updateUser(request, user);

        user = userRepository.save(user);

        return new GlobalResponse<>(
                Status.SUCCESS,
                new UserResponse(
                        user.getUsername(),
                        (String) jwt.getClaims().get("name"),
                        user.getEmail(),
                        user.getPhoneNumber(),
                        List.of(addressMapper.toAddressResponse(address))
                )
        );
    }
}
