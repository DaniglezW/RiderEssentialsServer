package com.rider.essentials.application.rest;

import com.rider.essentials.application.rest.api.IProductAPI;
import com.rider.essentials.domain.model.Product;
import com.rider.essentials.domain.services.interfaces.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;


@Slf4j
@RestController
public class ProductController implements IProductAPI {

    private final IProductService iProductService;

    @Autowired
    public ProductController(IProductService iProductService) {
        this.iProductService = iProductService;
    }

    @Override
    public Page<Product> getAllProducts(Integer page, Integer size) {
        log.info("Entering into ProductController.getAllProducts controller");
        Pageable pageable = PageRequest.of(page, size);
        try {
            return iProductService.getAllProducts(pageable);
        } catch (Exception e) {
            log.error("Error in ProductController.getAllProducts controller");
            throw e;
        }
    }

    @Override
    public Product getProduct(Long productId) {
        log.info("Entering into ProductController.getProduct controller");
        try {
            return iProductService.getProductById(productId);
        } catch (Exception e) {
            log.error("Error in ProductController.getProduct controller");
            throw e;
        }
    }

    @Override
    public Page<Product> getProductsByQuery(String query, Integer page, Integer size) {
        log.info("Entering into ProductController.getProductsByQuery controller");
        Pageable pageable = PageRequest.of(page, size);
        try {
            return iProductService.getProductsByQuery(query, pageable);
        } catch (Exception e) {
            log.error("Error in ProductController.getProductsByQuery controller");
            throw e;
        }
    }

    @Override
    public List<Product> getProductByPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        log.info("Entering into ProductController.getProductByPrice controller");
        try {
            return iProductService.getProductsByPriceRange(minPrice, maxPrice);
        } catch (Exception e) {
            log.error("Error in ProductController.getProductByPrice controller");
            throw e;
        }
    }

    @Override
    public List<Product> getAllProductsByCategory(Long categoryId) {
        log.info("Entering into ProductController.getAllProductsByCategory controller");
        try {
            return iProductService.getAllProductsByCategory(categoryId);
        } catch (Exception e) {
            log.error("Error in ProductController.getAllProductsByCategory controller");
            throw e;
        }
    }

}
