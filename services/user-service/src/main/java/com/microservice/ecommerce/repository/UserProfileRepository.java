//package com.microservice.ecommerce.repository;
//
//import com.microservice.ecommerce.model.entity.UserProfile;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
///**
// * ----------------------------------------------------------------------------
// * Author:        Hong Anh
// * Created on:    03/03/2025 at 8:23 PM
// * Project:       ecommerce-microservices
// * Contact:       https://github.com/lehonganh0201
// * ----------------------------------------------------------------------------
// */
//
//@Repository
//public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
//    @Query("SELECT p FROM UserProfile p WHERE p.user.id = :userId")
//    Optional<UserProfile> findByUserId(String userId);
//}
