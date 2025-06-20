package com.microservice.ecommerce.service.impl;

import com.microservice.ecommerce.mapper.AddressMapper;
import com.microservice.ecommerce.model.entity.Address;
import com.microservice.ecommerce.model.entity.User;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.Status;
import com.microservice.ecommerce.model.request.AddressRequest;
import com.microservice.ecommerce.model.response.AddressResponse;
import com.microservice.ecommerce.repository.AddressRepository;
import com.microservice.ecommerce.repository.UserRepository;
import com.microservice.ecommerce.service.AddressService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    05/04/2025 at 10:12 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Service
@RequiredArgsConstructor
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddressServiceImpl implements AddressService {
    AddressRepository addressRepository;
    UserRepository userRepository;
    AddressMapper addressMapper;

    @Override
    @Transactional
    public GlobalResponse<?> addAddress(AddressRequest request, Jwt jwt) {
        try {
            String userId = jwt.getSubject();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy người dùng với ID: " + userId));

            Address address = addressMapper.toAddress(request);
            address.setUser(user);

            if (request.isDefault()) {
                addressRepository.findByUser(user).forEach(existingAddress -> {
                    existingAddress.setIsDefault(false);
                    addressRepository.save(existingAddress);
                });
            }

            address = addressRepository.save(address);
            log.info("Đã thêm địa chỉ mới cho user {}: {}", userId, address.getId());

            return new GlobalResponse<>(
                    Status.SUCCESS,
                    addressMapper.toAddressResponse(address)
            );
        } catch (EntityNotFoundException ex) {
            log.error("Lỗi khi thêm địa chỉ: {}", ex.getMessage(), ex);
            return new GlobalResponse<>(Status.ERROR, ex.getMessage());
        } catch (Exception ex) {
            log.error("Lỗi không xác định khi thêm địa chỉ: {}", ex.getMessage(), ex);
            return new GlobalResponse<>(Status.ERROR, "Không thể thêm địa chỉ");
        }
    }

    @Override
    @Transactional
    public GlobalResponse<?> updateAddress(Integer addressId, AddressRequest request, Jwt jwt) {
        try {
            String userId = jwt.getSubject();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy người dùng với ID: " + userId));

            Address existingAddress = addressRepository.findById(addressId)
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy địa chỉ với ID: " + addressId));

            if (!existingAddress.getUser().getId().equals(userId)) {
                throw new AuthorizationDeniedException("Bạn không có quyền cập nhật địa chỉ này");
            }

            addressMapper.updateAddress(request, existingAddress);

            if (request.isDefault()) {
                addressRepository.findByUser(user).stream()
                        .filter(a -> !a.getId().equals(addressId))
                        .forEach(a -> {
                            a.setIsDefault(false);
                            addressRepository.save(a);
                        });
            }

            existingAddress = addressRepository.save(existingAddress);
            log.info("Đã cập nhật địa chỉ {} cho user {}", addressId, userId);

            return new GlobalResponse<>(
                    Status.SUCCESS,
                    addressMapper.toAddressResponse(existingAddress)
            );
        } catch (EntityNotFoundException | AuthorizationDeniedException ex) {
            log.error("Lỗi khi cập nhật địa chỉ: {}", ex.getMessage(), ex);
            return new GlobalResponse<>(Status.ERROR, ex.getMessage());
        } catch (Exception ex) {
            log.error("Lỗi không xác định khi cập nhật địa chỉ: {}", ex.getMessage(), ex);
            return new GlobalResponse<>(Status.ERROR, "Không thể cập nhật địa chỉ");
        }
    }

    @Override
    @Transactional
    public GlobalResponse<?> getOwnAddress(Jwt jwt) {
        try {
            String userId = jwt.getSubject();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy người dùng với ID: " + userId));

            List<Address> addresses = addressRepository.findByUser(user);
            List<AddressResponse> addressResponses = addresses.stream()
                    .map(addressMapper::toAddressResponse)
                    .toList();

            log.info("Đã lấy danh sách địa chỉ cho user {}", userId);

            return new GlobalResponse<>(
                    Status.SUCCESS,
                    addressResponses
            );
        } catch (EntityNotFoundException ex) {
            log.error("Lỗi khi lấy danh sách địa chỉ: {}", ex.getMessage(), ex);
            return new GlobalResponse<>(Status.ERROR, ex.getMessage());
        } catch (Exception ex) {
            log.error("Lỗi không xác định khi lấy danh sách địa chỉ: {}", ex.getMessage(), ex);
            return new GlobalResponse<>(Status.ERROR, "Không thể lấy danh sách địa chỉ");
        }
    }

    @Override
    @Transactional
    public GlobalResponse<?> getAddressById(Integer addressId, Jwt jwt) {
        try {
            String userId = jwt.getSubject();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy người dùng với ID: " + userId));

            Address address = addressRepository.findById(addressId)
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy địa chỉ với ID: " + addressId));

            if (!address.getUser().getId().equals(userId)) {
                throw new AuthorizationDeniedException("Bạn không có quyền truy cập địa chỉ này");
            }

            log.info("Đã lấy địa chỉ {} cho user {}", addressId, userId);

            return new GlobalResponse<>(
                    Status.SUCCESS,
                    addressMapper.toAddressResponse(address)
            );
        } catch (EntityNotFoundException | AuthorizationDeniedException ex) {
            log.error("Lỗi khi lấy địa chỉ: {}", ex.getMessage(), ex);
            return new GlobalResponse<>(Status.ERROR, ex.getMessage());
        } catch (Exception ex) {
            log.error("Lỗi không xác định khi lấy địa chỉ: {}", ex.getMessage(), ex);
            return new GlobalResponse<>(Status.ERROR, "Không thể lấy địa chỉ");
        }
    }
}