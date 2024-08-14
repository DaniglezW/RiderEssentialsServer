package com.rider.essentials.application.rest.api;


import com.rider.essentials.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.rider.essentials.application.constants.Constants.API_BASE;

@RequestMapping(API_BASE)
public interface IProductAPI {

    @GetMapping("/products")
    Page<Product> getAllProducts(
            @RequestParam(defaultValue = "0", value="page", required = false) Integer page,
            @RequestParam(defaultValue = "10", value="size", required = false) Integer size
    );

    @GetMapping("/products/{productId}")
    Product getProduct(@PathVariable("productId") Long productId);

    @GetMapping("/products/search")
    Page<Product> getProductsByQuery(
            @RequestParam("query") String query,
            @RequestParam(defaultValue = "0", value="page", required = false) Integer page,
            @RequestParam(defaultValue = "10", value="size", required = false) Integer size
    );

    @GetMapping("/products/category/{categoryId}")
    List<Product> getAllProductsByCategory(@PathVariable("categoryId") Long categoryId);

}
