package com.rider.essentials.repository;

import com.rider.essentials.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {



}
