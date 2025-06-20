package com.microservice.ecommerce.service.impl;

import com.microservice.ecommerce.exception.BusinessException;
import com.microservice.ecommerce.mapper.AddressMapper;
import com.microservice.ecommerce.mapper.UserMapper;
import com.microservice.ecommerce.model.entity.Address;
import com.microservice.ecommerce.model.entity.User;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.Status;
import com.microservice.ecommerce.model.request.UserRequest;
import com.microservice.ecommerce.model.response.AddressResponse;
import com.microservice.ecommerce.model.response.UserResponse;
import com.microservice.ecommerce.repository.AddressRepository;
import com.microservice.ecommerce.repository.UserRepository;
import com.microservice.ecommerce.service.UserService;
import com.microservice.ecommerce.util.CloudinaryUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    UserMapper userMapper;
    AddressMapper addressMapper;

    CloudinaryUtil cloudinaryUtil;

    @Override
    @Transactional
    public GlobalResponse<UserResponse> createUser(UserRequest request, Jwt jwt) {
        String userId = jwt.getSubject();
        if (userRepository.existsById(userId)) {
            throw new BusinessException("Người dùng với ID " + userId + " đã tồn tại");
        }

        User user = userMapper.toUser(request);
        user.setId(userId);

        Address address = null;
        if (request.address() != null) {
            address = addressMapper.toAddress(request.address());
            address.setUser(user);
            address.setIsDefault(true);
            address = addressRepository.save(address);
        }

        user = userRepository.save(user);

        UserResponse response = new UserResponse(
                (String) jwt.getClaims().get("name"),
                user.getPhoneNumber(),
                user.getAvatarUrl(),
                user.getGender(),
                user.getDateOfBirth(),
                address != null ? List.of(addressMapper.toAddressResponse(address)) : null
        );

        return new GlobalResponse<>(
                Status.SUCCESS,
                response
        );
    }

    @Override
    public GlobalResponse<UserResponse> findCurrentUser(Jwt jwt) {
        String userId = jwt.getSubject();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AuthorizationDeniedException("Không tìm thấy người dùng hiện tại"));

        List<Address> addresses = addressRepository.findByUser(user);
        List<AddressResponse> addressResponses = addresses.stream()
                .map(addressMapper::toAddressResponse)
                .toList();

        UserResponse response = new UserResponse(
                (String) jwt.getClaims().get("name"),
                user.getPhoneNumber(),
                user.getAvatarUrl(),
                user.getGender(),
                user.getDateOfBirth(),
                addressResponses
        );

        return new GlobalResponse<>(
                Status.SUCCESS,
                response
        );
    }

    @Override
    @Transactional
    public GlobalResponse<UserResponse> updateUser(Integer addressId, UserRequest request, Jwt jwt) {
        String userId = jwt.getSubject();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy người dùng hiện tại"));

        userMapper.updateUser(request, user);

        Address updatedAddress = null;
        if (addressId != null && request.address() != null) {
            Address address = addressRepository.findById(addressId)
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy địa chỉ với ID: " + addressId));
            if (!address.getUser().getId().equals(userId)) {
                throw new BusinessException("Địa chỉ này không thuộc về người dùng hiện tại");
            }
            addressMapper.updateAddress(request.address(), address);
            if (request.address().isDefault()) {
                addressRepository.findByUser(user).stream()
                        .filter(a -> !a.getId().equals(addressId))
                        .forEach(a -> {
                            a.setIsDefault(false);
                            addressRepository.save(a);
                        });
            }
            updatedAddress = addressRepository.save(address);
        }

        user = userRepository.save(user);

        List<Address> addresses = addressRepository.findByUser(user);
        List<AddressResponse> addressResponses = addresses.stream()
                .map(addressMapper::toAddressResponse)
                .toList();

        UserResponse response = new UserResponse(
                (String) jwt.getClaims().get("name"),
                user.getPhoneNumber(),
                user.getAvatarUrl(),
                user.getGender(),
                user.getDateOfBirth(),
                addressResponses
        );

        return new GlobalResponse<>(
                Status.SUCCESS,
                response
        );
    }

    @Override
    @Transactional
    public GlobalResponse<?> uploadAvatar(Jwt jwt, MultipartFile avatar) {
        try {
            String userId = jwt.getSubject();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy người dùng với ID: " + userId));

            if (avatar == null || avatar.isEmpty()) {
                throw new BusinessException("File ảnh không được để trống");
            }

            if (user.getAvatarUrl() != null) {
                try {
                    cloudinaryUtil.remove(user.getAvatarUrl());
                    log.info("Đã xóa ảnh avatar cũ cho user {}: {}", userId, user.getAvatarUrl());
                } catch (IOException e) {
                    log.error("Không thể xóa ảnh avatar cũ: {}", e.getMessage(), e);
                    throw new BusinessException("Không thể xóa ảnh avatar cũ: " + e.getMessage());
                }
            }

            String avatarUrl;
            try {
                avatarUrl = cloudinaryUtil.upload(avatar);
                user.setAvatarUrl(avatarUrl);
                log.info("Đã upload ảnh avatar mới cho user {}: {}", userId, avatarUrl);
            } catch (IOException e) {
                log.error("Không thể upload ảnh avatar: {}", e.getMessage(), e);
                throw new BusinessException("Không thể upload ảnh avatar: " + e.getMessage());
            }

            user = userRepository.save(user);

            List<Address> addresses = addressRepository.findByUser(user);
            List<AddressResponse> addressResponses = addresses.stream()
                    .map(addressMapper::toAddressResponse)
                    .toList();

            UserResponse response = new UserResponse(
                    (String) jwt.getClaims().get("name"),
                    user.getPhoneNumber(),
                    user.getAvatarUrl(),
                    user.getGender(),
                    user.getDateOfBirth(),
                    addressResponses
            );

            return new GlobalResponse<>(
                    Status.SUCCESS,
                    response
            );
        } catch (EntityNotFoundException | BusinessException ex) {
            log.error("Lỗi khi upload avatar: {}", ex.getMessage(), ex);
            return new GlobalResponse<>(Status.ERROR, ex.getMessage());
        } catch (Exception ex) {
            log.error("Lỗi không xác định khi upload avatar: {}", ex.getMessage(), ex);
            return new GlobalResponse<>(Status.ERROR, "Không thể upload avatar");
        }
    }
}
