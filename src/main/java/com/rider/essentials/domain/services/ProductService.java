package com.rider.essentials.domain.services;

import com.rider.essentials.domain.model.Product;
import com.rider.essentials.domain.services.interfaces.IProductService;
import com.rider.essentials.repository.IProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.rider.essentials.application.constants.Constants.DEFAULT_IMAGE_PRODUCT_ID;

@Slf4j
@Service
public class ProductService implements IProductService {

    private final IProductRepository iProductRepository;

    @Autowired
    public ProductService(IProductRepository iProductRepository) {
        this.iProductRepository = iProductRepository;
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        Page<Product> products = iProductRepository.findAll(pageable);
        List<Product> filteredProducts = products.stream()
                .filter(product -> product.getProductId() != DEFAULT_IMAGE_PRODUCT_ID)
                .toList();
        return new PageImpl<>(filteredProducts, pageable, products.getTotalElements());
    }

    @Override
    public Product getProductById(Long productId) {
        try {
            Product product = iProductRepository.findById(productId).orElse(null);
            return product != null && product.getProductId() == DEFAULT_IMAGE_PRODUCT_ID ? null : product;
        } catch (Exception e) {
            log.error("Error in ProductService.getProductById");
            throw e;
        }
    }

    @Override
    public Page<Product> getProductsByQuery(String query, Pageable pageable) {
        Page<Product> products = iProductRepository.findByNameContainingIgnoreCase(query, pageable);
        List<Product> filteredProducts = products.stream()
                .filter(product -> product.getProductId() != DEFAULT_IMAGE_PRODUCT_ID)
                .toList();
        return new PageImpl<>(filteredProducts, pageable, products.getTotalElements());
    }

    @Override
    public List<Product> getAllProductsByCategory(Long categoryId) {
        List<Product> products = iProductRepository.findByCategory_CategoryId(categoryId);
        return products.stream()
                .filter(product -> product.getProductId() != DEFAULT_IMAGE_PRODUCT_ID)
                .toList();
    }

    @Override
    public List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        try {
            List<Product> products = iProductRepository.findByPriceBetween(minPrice, maxPrice);
            return products.stream()
                    .filter(product -> product.getProductId() != DEFAULT_IMAGE_PRODUCT_ID)
                    .toList();
        } catch (Exception e) {
            log.error("Error in ProductService.getProductsByPriceRange");
            throw e;
        }
    }

}
