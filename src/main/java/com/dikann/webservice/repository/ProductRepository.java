package com.dikann.webservice.repository;

import com.dikann.webservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    void deleteByDiscountId(Long discountId);

    void deleteByCategoryId(Long categoryId);

    @Modifying
    @Query(value = "UPDATE product SET discount_id = NULL WHERE discount_id = ?1", nativeQuery = true)
    void updateAllProductsDiscountId(Long discountId);

}
