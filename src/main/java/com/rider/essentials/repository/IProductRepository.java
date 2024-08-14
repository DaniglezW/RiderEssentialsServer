package com.rider.essentials.repository;

import com.rider.essentials.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory_CategoryId(Long categoryId);

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
