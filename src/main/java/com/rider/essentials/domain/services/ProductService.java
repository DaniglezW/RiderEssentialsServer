package com.rider.essentials.domain.services;

import com.rider.essentials.domain.model.Product;
import com.rider.essentials.domain.services.interfaces.IProductService;
import com.rider.essentials.repository.IProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
public class ProductService implements IProductService {

    @Value("${default.image.path}")
    private String defaultImagePath;

    private static final String DEFAULT_IMG = "default.jpg";

    private final IProductRepository iProductRepository;

    @Autowired
    public ProductService(IProductRepository iProductRepository) {
        this.iProductRepository = iProductRepository;
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        Page<Product> productsPage = iProductRepository.findAll(pageable);
        return productsPage.map(this::setProductImage);
    }

    @Override
    public Product getProductById(Long productId) {
        Product product;
        try {
            product = iProductRepository.findById(productId).orElse(null);
            if (product != null) {
                setProductImage(product);
            }
            return product;
        } catch (Exception e) {
            log.error("Error in ProductService.getProductById");
            throw e;
        }
    }

    @Override
    public Page<Product> getProductsByQuery(String query, Pageable pageable) {
        Page<Product> productsPage = iProductRepository.findByNameContainingIgnoreCase(query, pageable);
        return productsPage.map(this::setProductImage);
    }

    @Override
    public List<Product> getAllProductsByCategory(Long categoryId) {
        List<Product> productsPage = iProductRepository.findByCategory_CategoryId(categoryId);
        return productsPage.stream().map(this::setProductImage).toList();
    }

    private Product setProductImage(Product product) {
        try {
            String imagePath = getImagePathOrDefault(product.getImageUrl());
            byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
            product.setImage(imageBytes);
        } catch (IOException e) {
            product.setImage(getDefaultImageBytes());
        }
        return product;
    }

    private String getImagePathOrDefault(String imageUrl) {
        if (imageUrl == null) {
            return Paths.get(defaultImagePath, DEFAULT_IMG).toString();
        }
        Path path = Paths.get(defaultImagePath, imageUrl);
        if (Files.exists(path)) {
            return path.toString();
        }
        return Paths.get(defaultImagePath, DEFAULT_IMG).toString();
    }

    private byte[] getDefaultImageBytes() {
        try {
            return Files.readAllBytes(Paths.get(defaultImagePath, DEFAULT_IMG));
        } catch (IOException e) {
            throw new RuntimeException("Default image not found", e);
        }
    }
}
