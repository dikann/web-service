package com.dikann.webservice.repository;

import com.dikann.webservice.entity.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByIdAndUserId(Long id, Long userId);

    Page<CartItem> findAllByUserId(Long userId, Pageable pageable);

    void deleteByIdAndUserId(Long id, Long userId);
}
