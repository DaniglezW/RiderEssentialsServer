package com.rider.essentials.domain.services;

import com.rider.essentials.domain.model.Product;
import com.rider.essentials.domain.services.interfaces.IProductService;
import com.rider.essentials.repository.IProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class ProductService implements IProductService {

    private final IProductRepository iProductRepository;

    @Autowired
    public ProductService(IProductRepository iProductRepository) {
        this.iProductRepository = iProductRepository;
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return iProductRepository.findAll(pageable);
    }

    @Override
    public Product getProductById(Long productId) {
        Product product;
        try {
            product = iProductRepository.findById(productId).orElse(null);
            return product;
        } catch (Exception e) {
            log.error("Error in ProductService.getProductById");
            throw e;
        }
    }

    @Override
    public Page<Product> getProductsByQuery(String query, Pageable pageable) {
        return iProductRepository.findByNameContainingIgnoreCase(query, pageable);
    }

    @Override
    public List<Product> getAllProductsByCategory(Long categoryId) {
        return iProductRepository.findByCategory_CategoryId(categoryId);
    }

    @Override
    public List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        try {
            return iProductRepository.findByPriceBetween(minPrice, maxPrice);
        } catch (Exception e) {
            log.error("Error in ProductService.getProductsByPriceRange");
            throw e;
        }
    }

}
