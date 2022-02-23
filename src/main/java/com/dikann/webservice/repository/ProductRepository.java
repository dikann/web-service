package com.dikann.webservice.repository;

import com.dikann.webservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    void deleteByDiscountId(Long discountId);

    void deleteByCategoryId(Long categoryId);

}
