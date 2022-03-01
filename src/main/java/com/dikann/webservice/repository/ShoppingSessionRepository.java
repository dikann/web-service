package com.dikann.webservice.repository;

import com.dikann.webservice.entity.ShoppingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingSessionRepository extends JpaRepository<ShoppingSession , Long> {
    @Query(value = "SELECT * FROM shopping_session WHERE valid=TRUE AND user_id= ?1 ORDER BY created_date DESC LIMIT 1" , nativeQuery = true)
    Optional<ShoppingSession> getActiveShoppingSession(Long userId);
}
