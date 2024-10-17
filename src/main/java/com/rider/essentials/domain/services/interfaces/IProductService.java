package com.rider.essentials.domain.services.interfaces;

import com.rider.essentials.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface IProductService {

    Page<Product> getAllProducts(Pageable pageable);

    Product getProductById(Long productId);

    Page<Product> getProductsByQuery(String query, Pageable pageable);

    List<Product> getAllProductsByCategory(Long categoryId);

    List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

}
