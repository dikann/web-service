package com.dikann.webservice.repository;

import com.dikann.webservice.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

    Optional<UserAddress> findByIdAndUserId(Long id, Long userId);

    List<UserAddress> findAllByUserId(Long userId);
}
