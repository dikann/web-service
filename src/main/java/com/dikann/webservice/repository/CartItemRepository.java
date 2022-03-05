package com.dikann.webservice.repository;

import com.dikann.webservice.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query(value = "SELECT * FROM cart_item \n" +
            "JOIN cart_item_shopping_session ON cart_item.id=cart_item_shopping_session.cart_item_id\n" +
            "JOIN shopping_session ON cart_item_shopping_session.shopping_session_id=shopping_session.id\n" +
            "WHERE cart_item.id= ?1 AND shopping_session.user_id= ?2", nativeQuery = true)
    Optional<CartItem> findByIdAndUserId(Long id, Long userId);

    @Query(value = "SELECT * FROM cart_item \n" +
            "JOIN cart_item_shopping_session ON cart_item.id=cart_item_shopping_session.cart_item_id\n" +
            "JOIN shopping_session ON cart_item_shopping_session.shopping_session_id=shopping_session.id\n" +
            "WHERE shopping_session.user_id= ?1 AND shopping_session.valid= ?2", nativeQuery = true)
    List<CartItem> findAllByUserIdAndValid(Long userId, boolean valid);
}
