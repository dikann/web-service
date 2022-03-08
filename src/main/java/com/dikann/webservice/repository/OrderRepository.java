package com.dikann.webservice.repository;

import com.dikann.webservice.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM orders\n" +
            "JOIN shopping_session ON orders.shopping_session_id = shopping_session.id\n" +
            "WHERE shopping_session.user_id= ?1 AND orders.id= ?2", nativeQuery = true)
    Optional<Order> findByIdAndUserId(Long userId, Long orderId);

    @Query(value = "SELECT * FROM orders\n" +
            "JOIN shopping_session ON orders.shopping_session_id = shopping_session.id\n" +
            "WHERE shopping_session.user_id= ?1", nativeQuery = true)
    List<Order> findAllByUserId(Long userId);
}
