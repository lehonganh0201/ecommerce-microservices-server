package com.microservice.ecommerce.service.impl;

import com.microservice.ecommerce.mapper.AddressMapper;
import com.microservice.ecommerce.model.entity.Address;
import com.microservice.ecommerce.model.entity.User;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.Status;
import com.microservice.ecommerce.model.request.AddressRequest;
import com.microservice.ecommerce.repository.AddressRepository;
import com.microservice.ecommerce.repository.UserRepository;
import com.microservice.ecommerce.service.AddressService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

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
    public GlobalResponse<?> addAddress(AddressRequest request, Jwt jwt) {
        try {
            String username = jwt.getSubject();
            User user = userRepository.findById(username)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));

            Address address = addressMapper.toAddress(request);
            address.setUser(user);
            addressRepository.save(address);

            return new GlobalResponse<>(Status.SUCCESS, "Address added successfully");
        } catch (Exception ex) {
            log.error("Error while adding address: {}", ex.getMessage(), ex);
            return new GlobalResponse<>(Status.ERROR, "Failed to add address");
        }
    }

    @Override
    public GlobalResponse<?> updateAddress(Integer addressId, AddressRequest request, Jwt jwt) {
        try {
            String username = jwt.getSubject();
            User user = userRepository.findById(username)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));

            Address existingAddress = addressRepository.findById(addressId)
                    .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + addressId));

            if (!existingAddress.getUser().getId().equals(user.getId())) {
                return new GlobalResponse<>(Status.ERROR, "You do not have permission to update this address");
            }

            addressMapper.updateAddress(request, existingAddress);
            addressRepository.save(existingAddress);

            return new GlobalResponse<>(Status.SUCCESS, "Address updated successfully");
        } catch (EntityNotFoundException ex) {
            return new GlobalResponse<>(Status.ERROR, ex.getMessage());
        } catch (Exception ex) {
            log.error("Error while updating address: {}", ex.getMessage(), ex);
            return new GlobalResponse<>(Status.ERROR, "Failed to update address");
        }
    }

    @Override
    public GlobalResponse<?> getOwnAddress(Jwt jwt) {
        try {
            String username = jwt.getSubject();
            User user = userRepository.findById(username)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));

            var addressList = addressRepository.findAllByUserId(user.getId());
            var addressDtoList = addressMapper.toDtoList(addressList);

            return new GlobalResponse<>(Status.SUCCESS, addressDtoList);
        } catch (EntityNotFoundException ex) {
            return new GlobalResponse<>(Status.ERROR, ex.getMessage());
        } catch (Exception ex) {
            log.error("Error while fetching addresses: {}", ex.getMessage(), ex);
            return new GlobalResponse<>(Status.ERROR, "Failed to get addresses");
        }
    }

    @Override
    public GlobalResponse<?> getAddressById(Integer addressId, Jwt jwt) {
        String username = jwt.getSubject();

        User user = userRepository.findById(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));

        if (!address.getUser().getId().equals(user.getId())) {
            return new GlobalResponse<>(
                    Status.ERROR,
                    "Access denied: Address does not belong to the current user"
            );
        }

        return new GlobalResponse<>(
                Status.SUCCESS,
                addressMapper.toAddressResponse(address)
        );
    }
}
